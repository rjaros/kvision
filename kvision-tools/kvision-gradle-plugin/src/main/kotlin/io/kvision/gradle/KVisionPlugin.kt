package io.kvision.gradle

import io.kvision.gradle.tasks.KVConvertPoTask
import io.kvision.gradle.tasks.KVGeneratePotTask
import io.kvision.gradle.tasks.KVWorkerBundleTask
import javax.inject.Inject
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Zip
import org.gradle.internal.os.OperatingSystem
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

abstract class KVisionPlugin @Inject constructor(
    private val providers: ProviderFactory
) : Plugin<Project> {

    private val logger: Logger = Logging.getLogger(KVisionPlugin::class.java)

//    private val executor: ExecOperations
//    private val fileOps: FileSystemOperations
//    private val providers: ProviderFactory
//    private val layout: ProjectLayout

    override fun apply(project: Project) = with(project) {
        logger.lifecycle("Applying KVision plugin")

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
     * Additionally, set default values for properties that require a [Project] instance.
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
     * This makes it easier to break-up project configuration into extension functions.
     */
    private data class KVPluginContext(
        private val project: Project,
        val kvExtension: KVisionExtension,
    ) : Project by project


    /** Configure a Kotlin JS project */
    private fun KVPluginContext.configureJsProject() {
        logger.lifecycle("configuring Kotlin/JS plugin")

        val kotlinJsExtension = extensions.getByType<KotlinJsProjectExtension>()

        registerGeneratePotFileTask {
            dependsOn(tasks.all.compileKotlinJs)

            inputs.files(kotlinJsExtension.sourceSets.main.get().kotlin.files)

            potFile.set(
                layout.projectDirectory.file(
                    "src/main/resources/i18n/messages.pot"
                )
            )
        }

        registerConvertPoToJsonTask {
            dependsOn(tasks.all.compileKotlinJs)

            sourceDirectory.set(
                layout.dir(tasks.provider.processResources.map { it.destinationDir })
            )
        }

        registerZipTask()

        afterEvaluate {
            tasks.all.processResources.configureEach {
                exclude("**/*.pot")
                dependsOn(tasks.all.compileKotlinJs)
            }
        }
    }


    /** Configure a Kotlin Multiplatform project */
    private fun KVPluginContext.configureMppProject() {
        logger.lifecycle("configuring Kotlin/MP plugin")

        val kotlinMppExtension = extensions.getByType<KotlinMultiplatformExtension>()

        registerGeneratePotFileTask {
            dependsOn(tasks.all.compileKotlinFrontend)
            inputs.files(kotlinMppExtension.sourceSets.frontendMain.map { it.kotlin.files })
            potFile.set(
                layout.projectDirectory.file(
                    "src/frontendMain/resources/i18n/messages.pot"
                )
            )
        }

        registerConvertPoToJsonTask {
            dependsOn(tasks.all.compileKotlinFrontend)
            sourceDirectory.set(
                layout.dir(
                    tasks.provider.frontendProcessResources.map { it.destinationDir }
                )
            )
        }

        registerWorkerBundleTask()

        tasks.all.compileKotlinFrontend.configureEach {
            dependsOn("compileCommonMainKotlinMetadata")
        }
        tasks.create("generateKVisionSources") {
            group = KVISION_TASK_GROUP
            description = "Generates KVision sources for fullstack interfaces"
            dependsOn("compileCommonMainKotlinMetadata")
        }

    }


    private fun KVPluginContext.registerGeneratePotFileTask(configuration: KVGeneratePotTask.() -> Unit = {}) {
        logger.lifecycle("registering KVGeneratePotTask")
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
        logger.lifecycle("registering KVConvertPoTask")
        return tasks.register<KVConvertPoTask>("convertPoToJson") {
            group = KVISION_TASK_GROUP

            enabled = kvExtension.enableGradleTasks.get()

            dependsOn(tasks.all.compileKotlinJs)

            po2jsonBinDir.set(
                rootNodeModulesDir.file("@rjaros/gettext.js/bin/po2json")
            )

            nodeJsBinary.set(nodeJsBinaryProvider())

            configuration()
        }
    }


    private fun KVPluginContext.registerZipTask(configuration: Zip.() -> Unit = {}) {
        logger.lifecycle("registering KVision zip task")

        val webDir = layout.projectDirectory.dir("src/main/web")

        tasks.register<Zip>("zip") {
            group = PACKAGE_TASK_GROUP
            description = "Builds ZIP archive with the application"

            enabled = kvExtension.enableGradleTasks.get()

            dependsOn(tasks.all.browserProductionWebpack)

            destinationDirectory.set(layout.buildDirectory.dir("libs"))
            from(tasks.provider.browserProductionWebpack.map { it.destinationDirectory }) {
                include("*.*")
            }
            from(webDir)

            duplicatesStrategy = DuplicatesStrategy.EXCLUDE

            configuration()
        }
    }


    /** Requires Kotlin MPP project */
    private fun KVPluginContext.registerWorkerBundleTask() {
        logger.lifecycle("registering KVWorkerBundleTask")

        tasks.register<KVWorkerBundleTask>("workerBundle") {
            dependsOn(tasks.all.workerBrowserProductionWebpack)

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
        logger.lifecycle("configuring Node")

        rootProject.configureYarn {
            if (kvExtension.enableSecureResolutions.get()) {
                val version = kvExtension.versions.async.get()
                resolution("async", version)

                val asyncVersion = resolutions.firstOrNull { it.path == "async" }?.let {
                    "YarnResolution(${it.path}, ${it.includedVersions}, ${it.excludedVersions})"
                }
                logger.lifecycle("[configureNodeEcosystem.configureYarn] set async version: $asyncVersion")
            }

            if (kvExtension.enableHiddenKotlinJsStore.get()) {
                lockFileDirectory = kvExtension.kotlinJsStoreDirectory.get().asFile
                logger.lifecycle("[configureNodeEcosystem.configureYarn] set lockFileDirectory: $lockFileDirectory")
            }
        }

        rootProject.configureNodeJs {
            if (kvExtension.enableWebpackVersions.get()) {
                versions.apply {

                    webpackDevServer.version = kvExtension.versions.webpackDevServer.get()
                    webpack.version = kvExtension.versions.webpack.get()
                    webpackCli.version = kvExtension.versions.webpackCli.get()
                    karma.version = kvExtension.versions.karma.get()
                    mocha.version = kvExtension.versions.mocha.get()

                    val versions = listOf(
                        "webpackDevServer: ${webpackDevServer.version}",
                        "         webpack: ${webpack.version}         ",
                        "      webpackCli: ${webpackCli.version}      ",
                        "           karma: ${karma.version}           ",
                        "           mocha: ${mocha.version}           ",
                    ).joinToString(", ") { it.trim() }
                    logger.lifecycle("[configureNodeEcosystem.configureNodeJs] set webpack versions: $versions")
                }
            }
        }

    }


    // task provider helpers - help make the script configurations shorter & more legible


    private val TaskContainer.provider: TaskProviders get() = TaskProviders(this)

    /** Lazy task provider. https://github.com/gradle/gradle/issues/16543 workaround */
    private inner class TaskProviders(private val tasks: TaskContainer) {

        val processResources: Provider<Copy>
            get() = provider("processResources")

        val compileKotlinFrontend: Provider<KotlinCompile<*>>
            get() = provider("compileKotlinFrontend")

        val compileKotlinJs: Provider<KotlinJsCompile>
            get() = provider("compileKotlinJs")

        val frontendProcessResources: Provider<Copy>
            get() = provider("frontendProcessResources")

        val browserProductionWebpack: Provider<KotlinWebpack>
            get() = provider("browserProductionWebpack")

        val workerBrowserProductionWebpack: Provider<Task>
            get() = provider("workerBrowserProductionWebpack")

        private inline fun <reified T : Task> provider(taskName: String): Provider<T> =
            providers
                .provider { taskName }
                .flatMap { tasks.named<T>(it) }
    }

    private val TaskContainer.all: TaskCollections get() = TaskCollections(this)

    private inner class TaskCollections(private val tasks: TaskContainer) {

        val processResources: TaskCollection<Copy>
            get() = collection("processResources")

        val compileKotlinFrontend: TaskCollection<KotlinCompile<*>>
            get() = collection("compileKotlinFrontend")

        val compileKotlinJs: TaskCollection<KotlinJsCompile>
            get() = collection("compileKotlinJs")

        val frontendProcessResources: TaskCollection<Copy>
            get() = collection("frontendProcessResources")

        val browserProductionWebpack: TaskCollection<KotlinWebpack>
            get() = collection("browserProductionWebpack")

        val workerBrowserProductionWebpack: TaskCollection<Task>
            get() = collection("workerBrowserProductionWebpack")


        private inline fun <reified T : Task> collection(taskName: String): TaskCollection<T> =
            tasks.withType<T>().matching { it.name == taskName }
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


        val isWindowsProvider = providers.provider {
            OperatingSystem.current().isWindows
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

    // source set provider helpers

    private val NamedDomainObjectContainer<KotlinSourceSet>.main: NamedDomainObjectProvider<KotlinSourceSet>
        get() = named("main")

    private val NamedDomainObjectContainer<KotlinSourceSet>.frontendMain: NamedDomainObjectProvider<KotlinSourceSet>
        get() = named("frontendMain")


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


        //

        private val Project.rootNodeModulesDir: DirectoryProperty
            get() = objects.directoryProperty()
                .convention(
                    rootProject.layout.buildDirectory.dir("js/node_modules/")
                )
    }

}
