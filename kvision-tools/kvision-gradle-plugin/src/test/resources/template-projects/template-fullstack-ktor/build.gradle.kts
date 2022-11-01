import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

// based on https://github.com/rjaros/kvision-examples/tree/master/template-fullstack-ktor

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("multiplatform") version kotlinVersion
//    val kvisionVersion: String by System.getProperties()
    id("io.kvision") // version kvisionVersion
    // note: the KVision plugin version is removed because the plugin is loaded from the test
    // class path, thanks to Gradle TestKit
}

version = "1.0.0-SNAPSHOT"
group = "com.example"

repositories {
    mavenCentral()
}

// Versions
val kotlinVersion: String by System.getProperties()
val kvisionVersion: String by System.getProperties()
val ktorVersion: String by project
val logbackVersion: String by project

val webDir = file("src/frontendMain/web")
val mainClassName = "io.ktor.server.netty.EngineMain"

kotlin {
    jvm("backend") {
        compilations.all {
            java {
                targetCompatibility = JavaVersion.VERSION_1_8
            }
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-Xjsr305=strict")
            }
        }
    }
    js("frontend") {
        browser {
            runTask {
                outputFileName = "main.bundle.js"
                sourceMaps = false
                devServer = KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 3000,
                    proxy = mutableMapOf(
                        "/kv/*" to "http://localhost:8080",
                        "/kvws/*" to mapOf("target" to "ws://localhost:8080", "ws" to true)
                    ),
                    static = mutableListOf("$buildDir/processedResources/frontend/main")
                )
            }
            webpackTask {
                outputFileName = "main.bundle.js"
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("io.kvision:kvision-server-ktor:$kvisionVersion")
            }
            kotlin.srcDir("build/generated-src/common")
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val backendMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-server-auth:$ktorVersion")
                implementation("io.ktor:ktor-server-compression:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
        val backendTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val frontendMain by getting {
            resources.srcDir(webDir)
            dependencies {
                implementation("io.kvision:kvision:$kvisionVersion")
                implementation("io.kvision:kvision-bootstrap:$kvisionVersion")
                implementation("io.kvision:kvision-bootstrap-css:$kvisionVersion")
                implementation("io.kvision:kvision-i18n:$kvisionVersion")
            }
            kotlin.srcDir("build/generated-src/frontend")
        }
        val frontendTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation("io.kvision:kvision-testutils:$kvisionVersion")
            }
        }
    }
}

afterEvaluate {
    tasks {
        create("frontendArchive", Jar::class).apply {
            dependsOn("frontendBrowserProductionWebpack")
            group = "package"
            archiveAppendix.set("frontend")
            val distribution =
                project.tasks.getByName(
                    "frontendBrowserProductionWebpack",
                    KotlinWebpack::class
                ).destinationDirectory!!
            from(distribution) {
                include("*.*")
            }
            from(webDir)
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            into("/assets")
            inputs.files(distribution, webDir)
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
        getByName("backendProcessResources", Copy::class) {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
        getByName("backendJar").group = "package"
        create("jar", Jar::class).apply {
            dependsOn("frontendArchive", "backendJar")
            group = "package"
            manifest {
                attributes(
                    mapOf(
                        "Implementation-Title" to rootProject.name,
                        "Implementation-Group" to rootProject.group,
                        "Implementation-Version" to rootProject.version,
                        "Timestamp" to System.currentTimeMillis(),
                        "Main-Class" to mainClassName
                    )
                )
            }
            val dependencies =
                configurations["backendRuntimeClasspath"].filter { it.name.endsWith(".jar") } +
                    project.tasks["backendJar"].outputs.files +
                    project.tasks["frontendArchive"].outputs.files
            dependencies.forEach {
                if (it.isDirectory) from(it) else from(zipTree(it))
            }
            exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
            inputs.files(dependencies)
            outputs.file(archiveFile)
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
        create("backendRun", JavaExec::class) {
            dependsOn("compileKotlinBackend")
            group = "run"
            main = mainClassName
            classpath =
                configurations["backendRuntimeClasspath"] + project.tasks["compileKotlinBackend"].outputs.files +
                    project.tasks["backendProcessResources"].outputs.files
            workingDir = buildDir
        }
    }
}
