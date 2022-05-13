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
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

abstract class KVisionPlugin : Plugin<Project> {

//    private val executor: ExecOperations
//    private val fileOps: FileSystemOperations
//    private val providers: ProviderFactory
//    private val layout: ProjectLayout

    override fun apply(project: Project) = with(project) {

        val kvExtension = createKVisionExtension()

        with(KVPluginContext(project, kvExtension)) {

            plugins.withId("org.jetbrains.kotlin.js") {
                configureJsProject()

                afterEvaluate {
                    configureNodeEcosystem()
                }
            }

            plugins.withId("org.jetbrains.kotlin.multiplatform") {
                configureMppProject()

                afterEvaluate {
                    configureNodeEcosystem()
                }
            }
        }
    }


    /**
     * Initialise the [KVisionExtension] on a [Project].
     *
     * Additionally, set config that requires a [Project] instance.
     */
    private fun Project.createKVisionExtension(): KVisionExtension {
        return extensions.create("kvision", KVisionExtension::class).apply {
            nodeBinaryPath.convention(nodeJsBinaryProvider())

            kotlinJsStoreDirectory.convention(project.layout.projectDirectory.dir(".kotlin-js-store"))
        }
    }


    /**
     * Helper class that provides both a Gradle [Project] and [KVisionExtension].
     *
     * Useful for extension functions that configure a project.
     */
    private data class KVPluginContext(
        private val project: Project,
        val kvExtension: KVisionExtension,
    ) : Project by project


    /** Configure a Kotlin JS project */
    private fun KVPluginContext.configureJsProject() {

        val kotlinJsExtension = extensions.getByType<KotlinJsProjectExtension>()

        registerGeneratePotFileTask {
            dependsOn(tasks.compileKotlinJs)

            inputs.files(kotlinJsExtension.sourceSets.main.get().kotlin.files)

            potFile.set(
                layout.projectDirectory.file(
                    "src/main/resources/i18n/messages.pot"
                )
            )
        }

        registerConvertPoToJsonTask {
            dependsOn(tasks.compileKotlinJs)

            sourceDirectory.set(
                layout.dir(tasks.processResources.map { it.destinationDir })
            )
        }

        registerZipTask()

        afterEvaluate {
            tasks.processResources.configure {
                exclude("**/*.pot")
                dependsOn(tasks.compileKotlinJs)
            }
        }
    }


    /** Configure a Kotlin Multiplatform project */
    private fun KVPluginContext.configureMppProject() {

        val kotlinMppExtension = extensions.getByType<KotlinMultiplatformExtension>()

        registerGeneratePotFileTask {
            dependsOn(tasks.compileKotlinFrontend)
            inputs.files(kotlinMppExtension.sourceSets.frontendMain.map { it.kotlin.files })
            potFile.set(
                layout.projectDirectory.file(
                    "src/frontendMain/resources/i18n/messages.pot"
                )
            )
        }

        registerConvertPoToJsonTask {
            dependsOn(tasks.compileKotlinFrontend)
            sourceDirectory.set(
                layout.dir(tasks.frontendProcessResources.map { it.destinationDir })
            )
        }

        registerWorkerBundleTask()

        tasks.getByName("compileKotlinFrontend") {
            dependsOn("compileCommonMainKotlinMetadata")
        }
        tasks.create("generateKVisionSources") {
            group = "KVision"
            description = "Generates KVision sources for fullstack interfaces"
            dependsOn("compileCommonMainKotlinMetadata")
        }

    }


    private fun KVPluginContext.registerGeneratePotFileTask(configuration: KVGeneratePotTask.() -> Unit = {}) {
        tasks.register<KVGeneratePotTask>("generatePotFile") {
            enabled = kvExtension.enableGradleTasks.get()

            nodeJsBinary.set(kvExtension.nodeBinaryPath)

            getTextExtractBin.set(
                rootNodeModulesDir.file("gettext-extract/bin/gettext-extract")
            )
            configuration()
        }
    }


    private fun KVPluginContext.registerConvertPoToJsonTask(
        configuration: KVConvertPoTask.() -> Unit = {}
    ): TaskProvider<KVConvertPoTask> {
        return tasks.register<KVConvertPoTask>("convertPoToJsonTask") {
            group = KVISION_TASK_GROUP

            enabled = kvExtension.enableGradleTasks.get()

            dependsOn(tasks.compileKotlinJs)

            po2jsonBinDir.set(
                rootNodeModulesDir.file("@rjaros/gettext.js/bin/po2json")
            )

            nodeJsBinary.set(nodeJsBinaryProvider())

            configuration()
        }
    }


    private fun KVPluginContext.registerZipTask(configuration: Zip.() -> Unit = {}) {
        val webDir = layout.projectDirectory.dir("src/main/web")

        tasks.register<Zip>("zip") {
            group = PACKAGE_TASK_GROUP
            description = "Builds ZIP archive with the application"

            enabled = kvExtension.enableGradleTasks.get()

            dependsOn(tasks.browserProductionWebpack)

            destinationDirectory.set(layout.buildDirectory.dir("libs"))
            from(tasks.browserProductionWebpack.map { it.destinationDirectory }) {
                include("*.*")
            }
            from(webDir)

            duplicatesStrategy = DuplicatesStrategy.EXCLUDE

            configuration()
        }
    }


    /** Requires Kotlin MPP project */
    private fun KVPluginContext.registerWorkerBundleTask() {
        tasks.register<KVWorkerBundleTask>("workerBundle") {
            dependsOn(tasks.workerBrowserProductionWebpack)

            enabled = kvExtension.enableWorkerTasks.get()

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

            inputs.dir(workerMainSrcDir)
            outputs.file(workerJsFile)

            executable(nodeJsBin)
            workingDir(
                rootProject.layout.buildDirectory.dir("js/packages/${rootProject.name}-worker")
            )
            args(
                webpackJs,
                "--config",
                webpackConfigJs,
            )
        }
    }


    private fun KVPluginContext.configureNodeEcosystem() {

        rootProject.configureYarn {
            if (kvExtension.enableSecureResolutions.get()) {
                resolution("async", kvExtension.versions.async.get())
            }

            if (kvExtension.enableHiddenKotlinJsStore.get()) {
                lockFileDirectory = kvExtension.kotlinJsStoreDirectory.get().asFile
            }
        }

        rootProject.configureNodeJs {
            if (kvExtension.enableWebpackVersions.get()) {
                versions.webpackDevServer.version = kvExtension.versions.webpackDevServer.get()
                versions.webpack.version = kvExtension.versions.webpack.get()
                versions.webpackCli.version = kvExtension.versions.webpackCli.get()
                versions.karma.version = kvExtension.versions.karma.get()
                versions.mocha.version = kvExtension.versions.mocha.get()
            }
        }

    }


    /**
     * Provider for the absolute path of the Node binary that Kotlin installs into the root project.
     *
     * The current operating system is taken into account.
     */
    private fun Project.nodeJsBinaryProvider(): Provider<String> {

        val nodeJsRootExtension = providers.provider {
            rootProject.extensions.getByType(NodeJsRootExtension::class)
        }

        val nodeDirProvider = nodeJsRootExtension
            .flatMap { it.nodeJsSetupTaskProvider }
            .map { it.destination }

        val isWindowsProvider = providers.systemProperty("os.name").map {
            it.toLowerCase().contains("windows")
        }

        val nodeBinDirProvider = isWindowsProvider.zip(nodeDirProvider) { isWindows, nodeDir ->
            if (isWindows) nodeDir else nodeDir.resolve("bin")
        }

        val finalCommandProvider = nodeJsRootExtension.zip(isWindowsProvider) { ext, isWindows ->
            if (isWindows && ext.nodeCommand == "node") "node.exe" else ext.nodeCommand
        }

        return finalCommandProvider.zip(nodeBinDirProvider) { finalCommand, nodeBinDir ->
            nodeBinDir.resolve(finalCommand).absolutePath
        }
    }


    companion object {

        const val KVISION_TASK_GROUP = "kvision"
        const val PACKAGE_TASK_GROUP = "package"

        private fun Project.configureYarn(configure: YarnRootExtension.() -> Unit) {
            plugins.withType<YarnPlugin> {
                rootProject.the<YarnRootExtension>().configure()
            }
        }

        private fun Project.configureNodeJs(configure: NodeJsRootExtension.() -> Unit) {
            rootProject.the<NodeJsRootExtension>().configure()
        }

        // task provider helpers - help make the script configurations shorter & more legible

        private val TaskContainer.compileKotlinFrontend: TaskProvider<KotlinCompile<*>>
            get() = named<KotlinCompile<*>>("compileKotlinFrontend")

        private val TaskContainer.compileKotlinJs: TaskProvider<KotlinJsCompile>
            get() = named<KotlinJsCompile>("compileKotlinJs")

        private val TaskContainer.processResources: TaskProvider<Copy>
            get() = named<Copy>("processResources")

        private val TaskContainer.frontendProcessResources: TaskProvider<Copy>
            get() = named<Copy>("frontendProcessResources")

        private val TaskContainer.browserProductionWebpack: TaskProvider<KotlinWebpack>
            get() = named<KotlinWebpack>("browserProductionWebpack")

        private val TaskContainer.workerBrowserProductionWebpack: TaskProvider<Task>
            get() = named("workerBrowserProductionWebpack")

        // source set provider helpers

        private val NamedDomainObjectContainer<KotlinSourceSet>.main: NamedDomainObjectProvider<KotlinSourceSet>
            get() = named("main")

        private val NamedDomainObjectContainer<KotlinSourceSet>.frontendMain: NamedDomainObjectProvider<KotlinSourceSet>
            get() = named("frontendMain")


        //

        private val Project.rootNodeModulesDir: DirectoryProperty
            get() = objects.directoryProperty()
                .convention(
                    rootProject.layout.buildDirectory.dir("js/node_modules/")
                )
    }
}
