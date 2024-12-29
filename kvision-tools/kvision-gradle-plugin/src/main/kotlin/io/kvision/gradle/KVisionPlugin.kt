package io.kvision.gradle

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
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
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.bundling.Zip
import org.gradle.internal.os.OperatingSystem
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun
import java.util.*
import javax.inject.Inject


enum class KVServerType {
    JAVALIN, JOOBY, KTOR, MICRONAUT, SPRINGBOOT, VERTX
}

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

            webDir.convention(project.layout.projectDirectory.dir("src/jsMain/web"))

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

        tasks.withType<Copy>().matching {
            it.name == "jsBrowserDistribution"
        }.configureEach {
            exclude("/modules/**")
        }

        val backendMainExists = layout.projectDirectory.dir("src/backendMain").asFile.exists()
        val jvmMainExists = layout.projectDirectory.dir("src/jvmMain").asFile.exists()
        if (backendMainExists && !jvmMainExists) {
            logger.warn("KVision Gradle plugin works now with standard source sets names. Please rename 'backendMain' source set and directory to `jvmMain`.")
        }
        val frontendMainExists = layout.projectDirectory.dir("src/frontendMain").asFile.exists()
        val jsMainExists = layout.projectDirectory.dir("src/jsMain").asFile.exists()
        if (frontendMainExists && !jsMainExists) {
            logger.warn("KVision Gradle plugin works now with standard source sets names. Please rename 'frontendMain' source set and directory to 'jsMain'.")
        }

        if (jsMainExists && jvmMainExists && kvExtension.enableKsp.get()) {
            if (!plugins.hasPlugin("java")) {
                plugins.apply("java")
            }
            plugins.apply("com.google.devtools.ksp")
        }

        val kotlinMppExtension = extensions.getByType<KotlinMultiplatformExtension>()

        if (jsMainExists && kvExtension.enableGradleTasks.get()) {
            registerGeneratePotFileTask {
                dependsOn(rootProject.tasks.all.kotlinNpmInstall)
                inputs.files(kotlinMppExtension.sourceSets.jsMain.map { it.kotlin.files })
                potFile.set(
                    layout.projectDirectory.file(
                        "src/jsMain/resources/i18n/messages.pot"
                    )
                )
            }
            val convertPoToJsonTask = registerConvertPoToJsonTask {
                dependsOn(rootProject.tasks.all.kotlinNpmInstall)
                sourceDirectory.set(
                    layout.projectDirectory.dir("src/jsMain/resources/i18n")
                )
                destinationDirectory.set(
                    kvExtension.generatedJsResources.dir("i18n")
                )
            }
            tasks.all.jsProcessResources.configureEach {
                exclude("**/*.pot")
                exclude("**/*.po")
                dependsOn(convertPoToJsonTask)
            }

            registerZipTask {
                dependsOn(tasks.all.jsBrowserDistribution)
            }

            kotlinMppExtension.sourceSets.matching { it.name == "jsMain" }.configureEach {
                resources.srcDir(kvExtension.webDir)
                resources.srcDir(kvExtension.generatedJsResources)
            }

            if (!jvmMainExists) {
                tasks.register("run") {
                    group = "run"
                    description = "Runs the application"
                    dependsOn("jsRun")
                }
            }
        }

        if (jsMainExists && jvmMainExists) {
            afterEvaluate {
                kotlinMppExtension.targets.configureEach {
                    compilations.configureEach {
                        compileTaskProvider.configure {
                            compilerOptions {
                                freeCompilerArgs.add("-Xexpect-actual-classes")
                            }
                        }
                    }
                }
            }
            if (kvExtension.enableKsp.get()) {
                tasks.all.compileKotlinJs.configureEach {
                    dependsOn("kspCommonMainKotlinMetadata")
                }

                tasks.all.compileKotlinJvm.configureEach {
                    dependsOn("kspCommonMainKotlinMetadata")
                }

                dependencies {
                    add("kspCommonMainMetadata", "io.kvision:kvision-ksp-processor:${kvVersions["versionNumber"]}")
                }

                afterEvaluate {
                    dependencies {
                        add("kspJs", "io.kvision:kvision-ksp-processor:${kvVersions["versionNumber"]}")
                    }
                    kotlinMppExtension.sourceSets.getByName("commonMain").kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
                    kotlinMppExtension.sourceSets.getByName("jsMain").kotlin.srcDir("build/generated/ksp/js/jsMain/kotlin")

                    // Workaround duplicated source roots in IntelliJ IDEA
                    afterEvaluate {
                        afterEvaluate {
                            afterEvaluate {
                                afterEvaluate {
                                    kotlinMppExtension.sourceSets.filter { it.name.startsWith("generatedByKsp") }
                                        .forEach {
                                            kotlinMppExtension.sourceSets.remove(it)
                                        }
                                }
                            }
                        }
                    }
                }

                tasks.all.kspKotlinJs.configureEach {
                    dependsOn("kspCommonMainKotlinMetadata")
                }

                if (kvExtension.enableGradleTasks.get()) {
                    tasks.register("generateKVisionSources") {
                        group = KVISION_TASK_GROUP
                        description = "Generates KVision sources for fullstack interfaces"
                        dependsOn("kspCommonMainKotlinMetadata")
                    }
                }
            }
            if (kvExtension.enableGradleTasks.get()) {
                afterEvaluate {
                    afterEvaluate {
                        val serverType = getServerType(project)
                        val assetsPath = when (serverType) {
                            KVServerType.MICRONAUT, KVServerType.SPRINGBOOT -> "/public"
                            KVServerType.VERTX -> "/webroot"
                            else -> "/assets"
                        }
                        tasks.register("jsArchive", Jar::class) {
                            dependsOn("jsBrowserDistribution")
                            group = "package"
                            archiveAppendix.set("js")
                            val distribution =
                                project.tasks.getByName(
                                    "jsBrowserDistribution",
                                    Copy::class
                                ).outputs
                            from(distribution)
                            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                            into(assetsPath)
                            inputs.files(distribution)
                            outputs.file(archiveFile)
                            manifest {
                                attributes(
                                    mapOf(
                                        "Implementation-Title" to rootProject.name,
                                        "Implementation-Group" to rootProject.group,
                                        "Implementation-Version" to rootProject.version,
                                        "Timestamp" to System.currentTimeMillis()
                                    )
                                )
                            }
                        }
                        tasks.getByName("jvmProcessResources", Copy::class) {
                            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                        }
                        when (serverType) {
                            KVServerType.JAVALIN, KVServerType.JOOBY, KVServerType.KTOR -> {
                                val jarTaskExists = tasks.findByName("jar") != null
                                val customJarTaskName = if (jarTaskExists) "shadowJar" else "jar"
                                tasks.register(customJarTaskName, Jar::class) {
                                    dependsOn("jsArchive", "jvmJar")
                                    group = "package"
                                    manifest {
                                        attributes(
                                            mapOf(
                                                "Implementation-Title" to rootProject.name,
                                                "Implementation-Group" to rootProject.group,
                                                "Implementation-Version" to rootProject.version,
                                                "Timestamp" to System.currentTimeMillis(),
                                                "Main-Class" to tasks.getByName(
                                                    "jvmRun",
                                                    JavaExec::class
                                                ).mainClass.get()
                                            )
                                        )
                                    }
                                    val dependencies = files(
                                        configurations.getByName("jvmRuntimeClasspath"),
                                        project.tasks["jvmJar"].outputs.files,
                                        project.tasks["jsArchive"].outputs.files
                                    )
                                    from(dependencies.asSequence().map { if (it.isDirectory) it else zipTree(it) }
                                        .asIterable())
                                    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
                                    inputs.files(dependencies)
                                    outputs.file(archiveFile)
                                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                                }
                                if (jarTaskExists) {
                                    tasks.getByName("jar", Jar::class).apply {
                                        enabled = false
                                        dependsOn("shadowJar")
                                    }
                                }
                            }

                            KVServerType.SPRINGBOOT -> {
                                tasks.getByName("bootJar", BootJar::class) {
                                    dependsOn("jsArchive", "jsMainClasses")
                                    classpath = files(
                                        kotlinMppExtension.targets["jvm"].compilations["main"].output.allOutputs,
                                        project.configurations["jvmRuntimeClasspath"],
                                        (project.tasks["jsArchive"] as Jar).archiveFile
                                    )
                                }
                                tasks.getByName("jar", Jar::class).apply {
                                    dependsOn("bootJar")
                                }
                                tasks.getByName("bootRun", BootRun::class) {
                                    dependsOn("jvmMainClasses")
                                    classpath = files(
                                        kotlinMppExtension.targets["jvm"].compilations["main"].output.allOutputs,
                                        project.configurations["jvmRuntimeClasspath"]
                                    )
                                }
                                tasks.getByName("jvmRun").apply {
                                    dependsOn("bootRun")
                                }
                            }

                            KVServerType.MICRONAUT -> {
                                tasks.getByName("shadowJar", ShadowJar::class) {
                                    dependsOn("jsArchive")
                                    from(project.tasks["jsArchive"].outputs.files)
                                    mergeServiceFiles()
                                }
                                if (kvExtension.enableKsp.get()) {
                                    tasks.getByName("kaptGenerateStubsKotlinJvm").apply {
                                        dependsOn("kspCommonMainKotlinMetadata")
                                    }
                                }
                                tasks.getByName("jar", Jar::class).apply {
                                    enabled = false
                                    dependsOn("shadowJar")
                                }
                                tasks.getByName("jvmRun").apply {
                                    dependsOn("run")
                                }
                            }

                            KVServerType.VERTX -> {
                                tasks.getByName("shadowJar", ShadowJar::class) {
                                    dependsOn("jsArchive")
                                    from(project.tasks["jsArchive"].outputs.files)
                                }
                                tasks.getByName("jar", Jar::class).apply {
                                    enabled = false
                                    dependsOn("shadowJar")
                                }
                                tasks.getByName("jvmRun").apply {
                                    dependsOn("vertxRun")
                                }
                            }

                            else -> {}
                        }
                    }
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
                    "jsBrowserDistribution",
                    Copy::class
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
                resolution("kvision-assets", kvVersions["kvisionAssetsVersion"]!!)
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
                resolution("electron", kvVersions["electronVersion"]!!)
                resolution("@electron/remote", kvVersions["electronRemoteVersion"]!!)
                resolution("@fortawesome/fontawesome-free", kvVersions["fontawesomeFreeVersion"]!!)
                resolution("handlebars", kvVersions["handlebarsVersion"]!!)
                resolution("handlebars-loader", kvVersions["handlebarsLoaderVersion"]!!)
                resolution("imask", kvVersions["imaskVersion"]!!)
                resolution("jquery", kvVersions["jqueryVersion"]!!)
                resolution("leaflet", kvVersions["leafletVersion"]!!)
                resolution("geojson", kvVersions["geojsonVersion"]!!)
                resolution("@types/geojson", kvVersions["geojsonTypesVersion"]!!)
                resolution("onsenui", kvVersions["onsenuiVersion"]!!)
                resolution("pace-progressbar", kvVersions["paceProgressbarVersion"]!!)
                resolution("print-js", kvVersions["printjsVersion"]!!)
                resolution("react", kvVersions["reactVersion"]!!)
                resolution("react-dom", kvVersions["reactVersion"]!!)
                resolution("trix", kvVersions["trixVersion"]!!)
                resolution("tabulator-tables", kvVersions["tabulatorTablesVersion"]!!)
                resolution("toastify-js", kvVersions["toastifyjsVersion"]!!)
                resolution("tom-select", kvVersions["tomSelectVersion"]!!)
            }

            if (kvExtension.enableHiddenKotlinJsStore.get()) {
                lockFileDirectory = kvExtension.kotlinJsStoreDirectory.get().asFile
                logger.info("[configureNodeEcosystem.configureYarn] set lockFileDirectory: $lockFileDirectory")
            }
        }
    }

    /** task provider helpers - help make the script configurations shorter & more legible */
    private val TaskContainer.provider: TaskProviders get() = TaskProviders(this)

    /** Lazy task providers */
    private inner class TaskProviders(private val tasks: TaskContainer) {

        val jsBrowserProductionWebpack: Provider<KotlinWebpack>
            get() = provider("jsBrowserProductionWebpack")

        // Workaround for https://github.com/gradle/gradle/issues/16543
        private inline fun <reified T : Task> provider(taskName: String): Provider<T> =
            providers
                .provider { taskName }
                .flatMap { tasks.named<T>(it) }
    }

    private val TaskContainer.all: TaskCollections get() = TaskCollections(this)

    /** Lazy task collections */
    private inner class TaskCollections(private val tasks: TaskContainer) {

        val jsProcessResources: TaskCollection<Copy>
            get() = collection("jsProcessResources")

        val compileKotlinJs: TaskCollection<KotlinCompilationTask<*>>
            get() = collection("compileKotlinJs")

        val compileKotlinJvm: TaskCollection<KotlinCompilationTask<*>>
            get() = collection("compileKotlinJvm")

        val jsBrowserDistribution: TaskCollection<Copy>
            get() = collection("jsBrowserDistribution")

        val workerBrowserProductionWebpack: TaskCollection<Task>
            get() = collection("workerBrowserProductionWebpack")

        val kotlinNpmInstall: TaskCollection<Task>
            get() = collection("kotlinNpmInstall")

        val kspKotlinJs: TaskCollection<Task>
            get() = collection("kspKotlinJs")

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

    private fun getServerType(project: Project): KVServerType? {
        val springbootDependencies = listOf(
            "spring-boot-starter-web", // for Spring Web MVC
            "spring-boot-starter-webflux", // for Spring WebFlux
        )
        val jvmMainImplementationDependencies = project.configurations["jvmMainImplementation"].dependencies
        if (jvmMainImplementationDependencies.any { it.name in springbootDependencies }) {
            return KVServerType.SPRINGBOOT
        }
        val kvisionServerDependency = project.configurations["commonMainApi"].dependencies.map {
            it.name
        }.firstOrNull { it.startsWith("kvision-server-") }
        return when (kvisionServerDependency) {
            "kvision-server-javalin" -> KVServerType.JAVALIN
            "kvision-server-jooby" -> KVServerType.JOOBY
            "kvision-server-ktor" -> KVServerType.KTOR
            "kvision-server-ktor-koin" -> KVServerType.KTOR
            "kvision-server-micronaut" -> KVServerType.MICRONAUT
            "kvision-server-spring-boot" -> KVServerType.SPRINGBOOT
            "kvision-server-vertx" -> KVServerType.VERTX
            else -> return null
        }
    }

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
