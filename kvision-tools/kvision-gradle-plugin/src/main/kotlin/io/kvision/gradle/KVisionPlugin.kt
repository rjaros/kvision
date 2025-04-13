package io.kvision.gradle

import io.kvision.gradle.tasks.KVConvertPoTask
import io.kvision.gradle.tasks.KVGeneratePotTask
import io.kvision.gradle.tasks.KVWorkerBundleTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Zip
import org.gradle.internal.os.OperatingSystem
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import java.util.*

abstract class KVisionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        logger.debug("Applying KVision plugin")

        val kvExtension = createKVisionExtension()

        val kvVersions = Properties().run {
            this@KVisionPlugin.javaClass.classLoader.getResourceAsStream("io.kvision.versions.properties")
                ?.let { this.load(it) }
            propertiesToMap(this)
        }

        with(KVPluginContext(project, kvExtension, kvVersions)) {

            plugins.withId("org.jetbrains.kotlin.js") {
                logger.warn("'kotlin(\"js\")' Gradle plugin is no longer supported by KVision. Please migrate to 'kotlin(\"multiplatform\")'.")
            }

            plugins.withId("org.jetbrains.kotlin.multiplatform") {
                configureProject()
                afterEvaluate {
                    configureNodeEcosystem()
                }
            }
        }
    }


    /**
     * Initialise the [KVisionExtension] on a [Project].
     *
     * Additionally, set default values for properties that require a [Project] instance.
     */
    private fun Project.createKVisionExtension(): KVisionExtension {
        return extensions.create("kvision", KVisionExtension::class).apply {
            nodeBinaryPath.convention(nodeJsBinaryProvider())

            kotlinJsStoreDirectory.convention(project.layout.projectDirectory.dir(".kotlin-js-store"))

            generatedJsResources.convention(project.layout.buildDirectory.dir("generated/kvision/jsResources"))
        }
    }


    /**
     * Helper class that provides both a Gradle [Project] and [KVisionExtension].
     *
     * This makes it easier to break-up project configuration into extension functions.
     */
    private data class KVPluginContext(
        private val project: Project,
        val kvExtension: KVisionExtension,
        val kvVersions: Map<String, String>
    ) : Project by project


    /** Configure a Kotlin Multiplatform project */
    private fun KVPluginContext.configureProject() {
        logger.debug("configuring Kotlin/MPP plugin")

        tasks.withType<Sync>().matching {
            it.name == "jsBrowserDistribution"
        }.configureEach {
            exclude("/modules/**", "/tailwind/**")
        }
        val jvmMainExists = layout.projectDirectory.dir("src/jvmMain").asFile.exists()
        val jsMainExists = layout.projectDirectory.dir("src/jsMain").asFile.exists()

        val kotlinMppExtension = extensions.getByType<KotlinMultiplatformExtension>()

        if (jsMainExists && kvExtension.enableGradleTasks.get()) {
            registerGeneratePotFileTask {
                dependsOn(rootProject.tasks.all.kotlinNpmInstall)
                inputs.files(kotlinMppExtension.sourceSets.jsMain.map { it.kotlin.files })
                potFile.set(
                    layout.projectDirectory.file(
                        "src/jsMain/resources/modules/i18n/messages.pot"
                    )
                )
            }
            val convertPoToJsonTask = registerConvertPoToJsonTask {
                dependsOn(rootProject.tasks.all.kotlinNpmInstall)
                sourceDirectory.set(
                    layout.projectDirectory.dir("src/jsMain/resources/modules/i18n")
                )
                destinationDirectory.set(
                    kvExtension.generatedJsResources.dir("modules/i18n")
                )
            }
            tasks.all.jsProcessResources.configureEach {
                exclude("**/*.pot")
                exclude("**/*.po")
                dependsOn(convertPoToJsonTask)
                eachFile {
                    if (this.name == "tailwind.config.js") {
                        this.filter {
                            it.replace(
                                "SOURCES",
                                project.layout.projectDirectory.dir("src").asFile.absolutePath + "/**/*.kt"
                            )
                        }
                    }
                }
            }

            registerZipTask {
                dependsOn(tasks.all.jsBrowserDistribution)
            }

            kotlinMppExtension.sourceSets.matching { it.name == "jsMain" }.configureEach {
                resources.srcDir(kvExtension.generatedJsResources)
            }

            if (!jvmMainExists) {
                tasks.register("run") {
                    group = "run"
                    description = "Runs the application"
                    dependsOn("jsBrowserDevelopmentRun")
                }
            }
        }

        if (kvExtension.enableWorkerTasks.get()) {
            registerWorkerBundleTask {
                dependsOn(tasks.all.workerBrowserProductionWebpack)
            }
        }
    }

    /** Applied to Kotlin Multiplatform project */
    private fun KVPluginContext.registerGeneratePotFileTask(
        configuration: KVGeneratePotTask.() -> Unit = {}
    ) {
        logger.debug("registering KVGeneratePotTask")
        tasks.withType<KVGeneratePotTask>().configureEach {
            nodeJsBinary.set(kvExtension.nodeBinaryPath)
            getTextExtractBin.set(
                rootNodeModulesDir.file("gettext-extract/bin/gettext-extract")
            )
            configuration()
        }
        tasks.register<KVGeneratePotTask>("generatePotFile")
    }


    /** Applied to Kotlin Multiplatform project */
    private fun KVPluginContext.registerConvertPoToJsonTask(
        configuration: KVConvertPoTask.() -> Unit = {}
    ): TaskProvider<KVConvertPoTask> {
        logger.debug("registering KVConvertPoTask")
        tasks.withType<KVConvertPoTask>().configureEach {
            enabled = kvExtension.enableGradleTasks.get()
            description = "Processes po files and converts them to JSON format"
            po2jsonBinDir.set(
                rootNodeModulesDir.file("gettext.js/bin/po2json")
            )
            nodeJsBinary.set(nodeJsBinaryProvider())
            configuration()
        }
        return tasks.register<KVConvertPoTask>("convertPoToJson")
    }

    /** Applied to Kotlin Multiplatform project */
    private fun KVPluginContext.registerZipTask(configuration: Zip.() -> Unit = {}) {
        logger.debug("registering KVision zip task")
        tasks.register<Zip>("zip") {
            group = PACKAGE_TASK_GROUP
            description = "Builds ZIP archive with the application"
            destinationDirectory.set(layout.buildDirectory.dir("libs"))
            val distribution =
                project.tasks.getByName(
                    "jsBrowserDistribution"
                ).outputs
            from(distribution)
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            configuration()
        }
    }

    /** Requires Kotlin MPP project */
    private fun KVPluginContext.registerWorkerBundleTask(configuration: KVWorkerBundleTask.() -> Unit = {}) {
        logger.debug("registering KVWorkerBundleTask")
        tasks.withType<KVWorkerBundleTask>().configureEach {
            nodeJsBin.set(nodeJsBinaryProvider())
            webpackJs.set(
                rootNodeModulesDir.file("webpack/bin/webpack.js")
            )
            webpackConfigJs.set(
                rootProject.layout.buildDirectory.file("js/packages/${rootProject.name}-worker/webpack.config.js")
            )
            workerMainSrcDir.set(
                layout.projectDirectory.dir("src/workerMain/kotlin")
            )
            workerJsFile.set(
                layout.buildDirectory.file("processedResources/frontend/main/worker.js")
            )
            executable(nodeJsBin.get())
            workingDir(
                rootProject.layout.buildDirectory.dir("js/packages/${rootProject.name}-worker")
            )
            args(
                webpackJs.get(),
                "--config",
                webpackConfigJs.get(),
            )
            configuration()
        }
        tasks.register<KVWorkerBundleTask>("workerBundle")
    }

    private fun KVPluginContext.configureNodeEcosystem() {
        logger.info("configuring Node")

        rootProject.extensions.configure<YarnRootExtension> {
            logger.info("configuring Yarn")
            if (kvExtension.enableResolutions.get() && kvVersions.isNotEmpty()) {
                resolution("bootstrap", kvVersions["bootstrapVersion"]!!)
                resolution("zzz-kvision-assets", kvVersions["kvisionAssetsVersion"]!!)
                resolution("css-loader", kvVersions["cssLoaderVersion"]!!)
                resolution("style-loader", kvVersions["styleLoaderVersion"]!!)
                resolution("imports-loader", kvVersions["importsLoaderVersion"]!!)
                resolution("fecha", kvVersions["fechaVersion"]!!)
                resolution("snabbdom", kvVersions["snabbdomVersion"]!!)
                resolution("@rjaros/snabbdom-virtualize", kvVersions["snabbdomVirtualizeVersion"]!!)
                resolution("split.js", kvVersions["splitjsVersion"]!!)
                resolution("gettext.js", kvVersions["gettextjsVersion"]!!)
                resolution("gettext-extract", kvVersions["gettextExtractVersion"]!!)
                resolution("karma-junit-reporter", kvVersions["karmaJunitReporterVersion"]!!)
                resolution("@popperjs/core", kvVersions["popperjsCoreVersion"]!!)
                resolution("bootstrap", kvVersions["bootstrapVersion"]!!)
                resolution("bootstrap-icons", kvVersions["bootstrapIconsVersion"]!!)
                resolution("bootstrap-fileinput", kvVersions["bootstrapFileinputVersion"]!!)
                resolution("chart.js", kvVersions["chartjsVersion"]!!)
                resolution("@eonasdan/tempus-dominus", kvVersions["tempusDominusVersion"]!!)
                resolution("@fortawesome/fontawesome-free", kvVersions["fontawesomeFreeVersion"]!!)
                resolution("handlebars", kvVersions["handlebarsVersion"]!!)
                resolution("handlebars-loader", kvVersions["handlebarsLoaderVersion"]!!)
                resolution("imask", kvVersions["imaskVersion"]!!)
                resolution("jquery", kvVersions["jqueryVersion"]!!)
                resolution("leaflet", kvVersions["leafletVersion"]!!)
                resolution("geojson", kvVersions["geojsonVersion"]!!)
                resolution("@types/geojson", kvVersions["geojsonTypesVersion"]!!)
                resolution("pace-progressbar", kvVersions["paceProgressbarVersion"]!!)
                resolution("print-js", kvVersions["printjsVersion"]!!)
                resolution("react", kvVersions["reactVersion"]!!)
                resolution("react-dom", kvVersions["reactVersion"]!!)
                resolution("trix", kvVersions["trixVersion"]!!)
                resolution("tabulator-tables", kvVersions["tabulatorTablesVersion"]!!)
                resolution("toastify-js", kvVersions["toastifyjsVersion"]!!)
                resolution("tom-select", kvVersions["tomSelectVersion"]!!)
                resolution("postcss", kvVersions["postcssVersion"]!!)
                resolution("postcss-loader", kvVersions["postcssLoaderVersion"]!!)
                resolution("tailwindcss", kvVersions["tailwindcssVersion"]!!)
                resolution("@tailwindcss/postcss", kvVersions["tailwindcssVersion"]!!)
                resolution("cssnano", kvVersions["cssnanoVersion"]!!)
            }

            if (kvExtension.enableHiddenKotlinJsStore.get()) {
                lockFileDirectory = kvExtension.kotlinJsStoreDirectory.get().asFile
                logger.info("[configureNodeEcosystem.configureYarn] set lockFileDirectory: $lockFileDirectory")
            }
        }
    }

    private val TaskContainer.all: TaskCollections get() = TaskCollections(this)

    /** Lazy task collections */
    private inner class TaskCollections(private val tasks: TaskContainer) {

        val jsProcessResources: TaskCollection<Copy>
            get() = collection("jsProcessResources")

        val jsBrowserDistribution: TaskCollection<Sync>
            get() = collection("jsBrowserDistribution")

        val workerBrowserProductionWebpack: TaskCollection<Task>
            get() = collection("workerBrowserProductionWebpack")

        val kotlinNpmInstall: TaskCollection<Task>
            get() = collection("kotlinNpmInstall")

        private inline fun <reified T : Task> collection(taskName: String): TaskCollection<T> =
            tasks.withType<T>().matching { it.name == taskName }
    }

    /**
     * [Provider] for the absolute path of the Node binary that the Kotlin plugin (JS or
     * Multiplatform) installs into the root project.
     *
     * The current operating system is taken into account.
     */
    @Suppress("DEPRECATION")
    private fun Project.nodeJsBinaryProvider(): Provider<String> {
        val nodeJsRootExtension = providers.provider {
            rootProject.extensions.getByType(NodeJsRootExtension::class)
        }
        val nodeDirProvider = nodeJsRootExtension
            .flatMap { it.nodeJsSetupTaskProvider }
            .map { it.destination }
        val isWindowsProvider = providers.provider {
            OperatingSystem.current().isWindows
        }
        val nodeBinDirProvider = isWindowsProvider.zip(nodeDirProvider) { isWindows, nodeDir ->
            if (isWindows) nodeDir else nodeDir.resolve("bin")
        }
        val nodeExecutableProvider = nodeJsRootExtension.zip(isWindowsProvider) { ext, isWindows ->
            if (isWindows && ext.command == "node") "node.exe" else ext.command
        }
        return nodeExecutableProvider.zip(nodeBinDirProvider) { nodeExecutable, nodeBinDir ->
            nodeBinDir.resolve(nodeExecutable).absolutePath
        }
    }

    // source set provider helpers

    private val NamedDomainObjectContainer<KotlinSourceSet>.jsMain: NamedDomainObjectProvider<KotlinSourceSet>
        get() = named("jsMain")

    private fun propertiesToMap(prop: Properties): Map<String, String> {
        val retMap = mutableMapOf<String, String>()
        for ((key, value) in prop) {
            retMap[key.toString()] = value.toString()
        }
        return retMap
    }

    companion object {

        const val KVISION_TASK_GROUP = "kvision"
        const val PACKAGE_TASK_GROUP = "package"

        private val Project.rootNodeModulesDir: DirectoryProperty
            get() = objects.directoryProperty().convention(
                rootProject.layout.buildDirectory.dir("js/node_modules/")
            )
    }

}
