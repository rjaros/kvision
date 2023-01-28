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
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Zip
import org.gradle.internal.os.OperatingSystem
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

abstract class KVisionPlugin @Inject constructor(
    private val providers: ProviderFactory
) : Plugin<Project> {

//    private val executor: ExecOperations
//    private val fileOps: FileSystemOperations
//    private val providers: ProviderFactory
//    private val layout: ProjectLayout

    override fun apply(target: Project) = with(target) {
        logger.debug("Applying KVision plugin")

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

            generatedFrontendResources.convention(project.layout.buildDirectory.dir("generated/kvision/frontendResources"))
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
        logger.debug("configuring Kotlin/JS plugin")

        val kotlinJsExtension = extensions.getByType<KotlinJsProjectExtension>()

        if (kvExtension.enableGradleTasks.get()) {
            registerGeneratePotFileTask {
                dependsOn(tasks.all.compileKotlinJs)
                inputs.files(kotlinJsExtension.sourceSets.main.get().kotlin.files)
                potFile.set(
                    layout.projectDirectory.file(
                        "src/main/resources/i18n/messages.pot"
                    )
                )
            }
        }

        val convertPoToJsonTask = registerConvertPoToJsonTask {
            dependsOn(tasks.all.compileKotlinJs)
            sourceDirectory.set(
                layout.projectDirectory.dir("src/main/resources/i18n")
            )
            destinationDirectory.set(
                kvExtension.generatedFrontendResources.dir("i18n")
            )
        }

        if (kvExtension.enableGradleTasks.get()) {
            registerZipTask {
                dependsOn(tasks.all.browserProductionWebpack)
            }
        }

        tasks.all.processResources.configureEach {
            exclude("**/*.pot")
            exclude("**/*.po")
            dependsOn(convertPoToJsonTask)
        }

        if (kvExtension.irCompiler.get()) {
            tasks.all.browserDevelopmentRun.configureEach {
                dependsOn("developmentExecutableCompileSync")
            }
        }

        kotlinJsExtension.sourceSets.main.configure {
            resources.srcDir(kvExtension.generatedFrontendResources)
        }
    }


    /** Configure a Kotlin Multiplatform project */
    private fun KVPluginContext.configureMppProject() {
        logger.debug("configuring Kotlin/MPP plugin")

        if (kvExtension.enableKsp.get()) plugins.apply("com.google.devtools.ksp")

        val kotlinMppExtension = extensions.getByType<KotlinMultiplatformExtension>()

        if (kvExtension.enableGradleTasks.get()) {
            registerGeneratePotFileTask {
                dependsOn(tasks.all.compileKotlinFrontend)
                inputs.files(kotlinMppExtension.sourceSets.frontendMain.map { it.kotlin.files })
                potFile.set(
                    layout.projectDirectory.file(
                        "src/frontendMain/resources/i18n/messages.pot"
                    )
                )
            }
        }

        val convertPoToJsonTask = registerConvertPoToJsonTask {
            dependsOn(tasks.all.compileKotlinFrontend)
            sourceDirectory.set(
                layout.projectDirectory.dir("src/frontendMain/resources/i18n")
            )
            destinationDirectory.set(
                kvExtension.generatedFrontendResources.dir("i18n")
            )
        }

        if (kvExtension.enableWorkerTasks.get()) {
            registerWorkerBundleTask {
                dependsOn(tasks.all.workerBrowserProductionWebpack)
            }
        }

        tasks.all.compileKotlinFrontend.configureEach {
            if (kvExtension.enableKsp.get()) dependsOn("kspCommonMainKotlinMetadata")
        }

        tasks.all.compileKotlinBackend.configureEach {
            if (kvExtension.enableKsp.get()) dependsOn("kspCommonMainKotlinMetadata")
        }

        if (kvExtension.enableGradleTasks.get() && kvExtension.enableKsp.get()) {
            tasks.create("generateKVisionSources") {
                group = KVISION_TASK_GROUP
                description = "Generates KVision sources for fullstack interfaces"
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }

        tasks.all.frontendProcessResources.configureEach {
            exclude("**/*.pot")
            exclude("**/*.po")
            dependsOn(convertPoToJsonTask)
        }

        if (kvExtension.irCompiler.get()) {
            tasks.all.frontendBrowserDevelopmentRun.configureEach {
                dependsOn("frontendDevelopmentExecutableCompileSync")
            }
        }

        kotlinMppExtension.sourceSets.matching { it.name == "frontendMain" }.configureEach {
            resources.srcDir(kvExtension.generatedFrontendResources)
        }

        if (kvExtension.enableKsp.get()) {
            dependencies {
                add("kspCommonMainMetadata", "io.kvision:kvision-ksp-processor:6.1.0")
            }

            afterEvaluate {
                dependencies {
                    add("kspFrontend", "io.kvision:kvision-ksp-processor:6.1.0")
                }
                kotlinMppExtension.sourceSets.getByName("commonMain").kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
                kotlinMppExtension.sourceSets.getByName("frontendMain").kotlin.srcDir("build/generated/ksp/frontend/frontendMain/kotlin")
            }

            tasks.all.kspKotlinFrontend.configureEach {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }
    }

    /** Applied to both Kotlin JS and Kotlin Multiplatform project */
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


    /** Applied to both Kotlin JS and Kotlin Multiplatform project */
    private fun KVPluginContext.registerConvertPoToJsonTask(
        configuration: KVConvertPoTask.() -> Unit = {}
    ): TaskProvider<KVConvertPoTask> {
        logger.debug("registering KVConvertPoTask")
        tasks.withType<KVConvertPoTask>().configureEach {
            enabled = kvExtension.enableGradleTasks.get()
            po2jsonBinDir.set(
                rootNodeModulesDir.file("gettext.js/bin/po2json")
            )
            nodeJsBinary.set(nodeJsBinaryProvider())
            configuration()
        }
        return tasks.register<KVConvertPoTask>("convertPoToJson")
    }


    /** Requires Kotlin JS project */
    private fun KVPluginContext.registerZipTask(configuration: Zip.() -> Unit = {}) {
        logger.debug("registering KVision zip task")
        val webDir = layout.projectDirectory.dir("src/main/web")
        tasks.register<Zip>("zip") {
            group = PACKAGE_TASK_GROUP
            description = "Builds ZIP archive with the application"
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
            if (kvExtension.enableResolutions.get()) {
                // No forced resolutions at the moment
            }

            if (kvExtension.enableHiddenKotlinJsStore.get()) {
                lockFileDirectory = kvExtension.kotlinJsStoreDirectory.get().asFile
                logger.info("[configureNodeEcosystem.configureYarn] set lockFileDirectory: $lockFileDirectory")
            }
        }

        rootProject.extensions.configure<NodeJsRootExtension> {
            logger.info("configuring NodeJs")
            if (kvExtension.enableWebpackVersions.get()) {
                versions.apply {
                    kvExtension.versions.webpackDevServer.orNull?.let {
                        webpackDevServer.version = it
                    }
                    kvExtension.versions.webpack.orNull?.let {
                        webpack.version = it
                    }
                    kvExtension.versions.webpackCli.orNull?.let {
                        webpackCli.version = it
                    }
                    kvExtension.versions.karma.orNull?.let {
                        karma.version = it
                    }
                    kvExtension.versions.mocha.orNull?.let {
                        mocha.version = it
                    }
                    val versions = listOf(
                        "webpackDevServer: ${webpackDevServer.version}",
                        "         webpack: ${webpack.version}         ",
                        "      webpackCli: ${webpackCli.version}      ",
                        "           karma: ${karma.version}           ",
                        "           mocha: ${mocha.version}           ",
                    ).joinToString(", ") { it.trim() }
                    logger.info("[configureNodeEcosystem.configureNodeJs] set webpack versions: $versions")
                }
            }
        }
    }

    /** task provider helpers - help make the script configurations shorter & more legible */
    private val TaskContainer.provider: TaskProviders get() = TaskProviders(this)

    /** Lazy task providers */
    private inner class TaskProviders(private val tasks: TaskContainer) {

        val processResources: Provider<Copy>
            get() = provider("processResources")

        val frontendProcessResources: Provider<Copy>
            get() = provider("frontendProcessResources")

        val browserProductionWebpack: Provider<KotlinWebpack>
            get() = provider("browserProductionWebpack")

        // Workaround for https://github.com/gradle/gradle/issues/16543
        private inline fun <reified T : Task> provider(taskName: String): Provider<T> =
            providers
                .provider { taskName }
                .flatMap { tasks.named<T>(it) }
    }

    private val TaskContainer.all: TaskCollections get() = TaskCollections(this)

    /** Lazy task collections */
    private inner class TaskCollections(private val tasks: TaskContainer) {

        val processResources: TaskCollection<Copy>
            get() = collection("processResources")

        val frontendProcessResources: TaskCollection<Copy>
            get() = collection("frontendProcessResources")

        val compileKotlinFrontend: TaskCollection<KotlinCompile<*>>
            get() = collection("compileKotlinFrontend")

        val compileKotlinBackend: TaskCollection<KotlinCompile<*>>
            get() = collection("compileKotlinBackend")

        val compileKotlinJs: TaskCollection<KotlinJsCompile>
            get() = collection("compileKotlinJs")

        val browserProductionWebpack: TaskCollection<KotlinWebpack>
            get() = collection("browserProductionWebpack")

        val workerBrowserProductionWebpack: TaskCollection<Task>
            get() = collection("workerBrowserProductionWebpack")

        val browserDevelopmentRun: TaskCollection<Task>
            get() = collection("browserDevelopmentRun")

        val frontendBrowserDevelopmentRun: TaskCollection<Task>
            get() = collection("frontendBrowserDevelopmentRun")

        val kspKotlinFrontend: TaskCollection<Task>
            get() = collection("kspKotlinFrontend")

        private inline fun <reified T : Task> collection(taskName: String): TaskCollection<T> =
            tasks.withType<T>().matching { it.name == taskName }
    }

    /**
     * [Provider] for the absolute path of the Node binary that the Kotlin plugin (JS or
     * Multiplatform) installs into the root project.
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
        val nodeExecutableProvider = nodeJsRootExtension.zip(isWindowsProvider) { ext, isWindows ->
            if (isWindows && ext.nodeCommand == "node") "node.exe" else ext.nodeCommand
        }
        return nodeExecutableProvider.zip(nodeBinDirProvider) { nodeExecutable, nodeBinDir ->
            nodeBinDir.resolve(nodeExecutable).absolutePath
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

        private val Project.rootNodeModulesDir: DirectoryProperty
            get() = objects.directoryProperty().convention(
                rootProject.layout.buildDirectory.dir("js/node_modules/")
            )
    }

}
