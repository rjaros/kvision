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
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.tomlj.Toml
import java.util.*

abstract class KVisionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        logger.debug("Applying KVision plugin")

        val kvExtension = createKVisionExtension()

        val kvVersions = try {
            val versions = Toml.parse(this@KVisionPlugin.javaClass.classLoader.getResourceAsStream("io.kvision.versions.toml"))
            versions.toMap().mapNotNull { (k, v) -> if (v is String) k to v else null }.toMap()
        } catch (e: Exception) {
            logger.warn("Failed to load versions from io.kvision.versions.toml, using empty versions map", e)
            emptyMap<String, String>()
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
            val srcDir = project.layout.projectDirectory.dir("src").asFile.absolutePath + "/**/*.kt"
            tasks.all.jsProcessResources.configureEach {
                exclude("**/*.pot")
                exclude("**/*.po")
                dependsOn(convertPoToJsonTask)
                eachFile {
                    if (this.name == "tailwind.config.js") {
                        this.filter {
                            it.replace(
                                "SOURCES",
                                srcDir
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

        rootProject.extensions.findByType<YarnRootExtension>()?.apply {
            logger.info("configuring Yarn")
            if (kvExtension.enableResolutions.get() && kvVersions.isNotEmpty()) {
                resolution("zzz-kvision-assets", kvVersions["npm-kvision-assets"]!!)
                resolution("css-loader", kvVersions["css-loader"]!!)
                resolution("style-loader", kvVersions["style-loader"]!!)
                resolution("imports-loader", kvVersions["imports-loader"]!!)
                resolution("fecha", kvVersions["fecha"]!!)
                resolution("snabbdom", kvVersions["snabbdom"]!!)
                resolution("@rjaros/snabbdom-virtualize", kvVersions["snabbdom-virtualize"]!!)
                resolution("split.js", kvVersions["splitjs"]!!)
                resolution("gettext.js", kvVersions["gettext-js"]!!)
                resolution("gettext-extract", kvVersions["gettext-extract"]!!)
                resolution("karma-junit-reporter", kvVersions["karma-junit-reporter"]!!)
                resolution("@popperjs/core", kvVersions["popperjs-core"]!!)
                resolution("bootstrap", kvVersions["bootstrap"]!!)
                resolution("bootstrap-icons", kvVersions["bootstrap-icons"]!!)
                resolution("bootstrap-fileinput", kvVersions["bootstrap-fileinput"]!!)
                resolution("chart.js", kvVersions["chartjs"]!!)
                resolution("@eonasdan/tempus-dominus", kvVersions["tempus-dominus"]!!)
                resolution("@fortawesome/fontawesome-free", kvVersions["fontawesome"]!!)
                resolution("handlebars", kvVersions["handlebars"]!!)
                resolution("handlebars-loader", kvVersions["handlebars-loader"]!!)
                resolution("imask", kvVersions["imask"]!!)
                resolution("jquery", kvVersions["jquery"]!!)
                resolution("leaflet", kvVersions["leaflet"]!!)
                resolution("geojson", kvVersions["geojson"]!!)
                resolution("pace-progressbar", kvVersions["pace-progressbar"]!!)
                resolution("print-js", kvVersions["printjs"]!!)
                resolution("react", kvVersions["react"]!!)
                resolution("react-dom", kvVersions["react"]!!)
                resolution("trix", kvVersions["trix"]!!)
                resolution("tabulator-tables", kvVersions["tabulator"]!!)
                resolution("toastify-js", kvVersions["toastify"]!!)
                resolution("tom-select", kvVersions["tom-select"]!!)
                resolution("postcss", kvVersions["postcss"]!!)
                resolution("postcss-loader", kvVersions["postcss-loader"]!!)
                resolution("tailwindcss", kvVersions["tailwindcss"]!!)
                resolution("@tailwindcss/postcss", kvVersions["tailwindcss"]!!)
                resolution("cssnano", kvVersions["cssnano"]!!)
            }
            if (kvExtension.enableHiddenKotlinJsStore.get()) {
                lockFileDirectory = kvExtension.kotlinJsStoreDirectory.get().asFile
                logger.info("[configureNodeEcosystem.configureYarn] set lockFileDirectory: $lockFileDirectory")
            }
        }
        rootProject.extensions.findByType<org.jetbrains.kotlin.gradle.targets.js.npm.NpmExtension>()?.apply {
            logger.debug("configuring Npm")
            if (kvExtension.enableResolutions.get() && kvVersions.isNotEmpty()) {
                override("zzz-kvision-assets", kvVersions["npm-kvision-assets"]!!)
                override("css-loader", kvVersions["css-loader"]!!)
                override("style-loader", kvVersions["style-loader"]!!)
                override("imports-loader", kvVersions["imports-loader"]!!)
                override("fecha", kvVersions["fecha"]!!)
                override("snabbdom", kvVersions["snabbdom"]!!)
                override("@rjaros/snabbdom-virtualize", kvVersions["snabbdom-virtualize"]!!)
                override("split.js", kvVersions["splitjs"]!!)
                override("gettext.js", kvVersions["gettext-js"]!!)
                override("gettext-extract", kvVersions["gettext-extract"]!!)
                override("karma-junit-reporter", kvVersions["karma-junit-reporter"]!!)
                override("@popperjs/core", kvVersions["popperjs-core"]!!)
                override("bootstrap", kvVersions["bootstrap"]!!)
                override("bootstrap-icons", kvVersions["bootstrap-icons"]!!)
                override("bootstrap-fileinput", kvVersions["bootstrap-fileinput"]!!)
                override("chart.js", kvVersions["chartjs"]!!)
                override("@eonasdan/tempus-dominus", kvVersions["tempus-dominus"]!!)
                override("@fortawesome/fontawesome-free", kvVersions["fontawesome"]!!)
                override("handlebars", kvVersions["handlebars"]!!)
                override("handlebars-loader", kvVersions["handlebars-loader"]!!)
                override("imask", kvVersions["imask"]!!)
                override("jquery", kvVersions["jquery"]!!)
                override("leaflet", kvVersions["leaflet"]!!)
                override("geojson", kvVersions["geojson"]!!)
                override("pace-progressbar", kvVersions["pace-progressbar"]!!)
                override("print-js", kvVersions["printjs"]!!)
                override("react", kvVersions["react"]!!)
                override("react-dom", kvVersions["react-dom"]!!)
                override("trix", kvVersions["trix"]!!)
                override("tabulator-tables", kvVersions["tabulator"]!!)
                override("toastify-js", kvVersions["toastify"]!!)
                override("tom-select", kvVersions["tom-select"]!!)
                override("postcss", kvVersions["postcss"]!!)
                override("postcss-loader", kvVersions["postcss-loader"]!!)
                override("tailwindcss", kvVersions["tailwindcss"]!!)
                override("@tailwindcss/postcss", kvVersions["tailwindcss"]!!)
                override("cssnano", kvVersions["cssnano"]!!)
            }
            if (kvExtension.enableHiddenKotlinJsStore.get()) {
                lockFileDirectory.set(kvExtension.kotlinJsStoreDirectory.get().asFile)
                logger.info("[configureNodeEcosystem.configureNpm] set lockFileDirectory: $lockFileDirectory")
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
     * Multiplatform) installs into the project.
     */
    private fun Project.nodeJsBinaryProvider(): Provider<String> {
        val nodeJsEnvSpec = providers.provider {
            extensions.getByType(NodeJsEnvSpec::class)
        }
        return nodeJsEnvSpec.flatMap { it.executable }
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
