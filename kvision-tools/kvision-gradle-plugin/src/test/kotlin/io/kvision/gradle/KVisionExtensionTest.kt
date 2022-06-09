package io.kvision.gradle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import io.kvision.gradle.util.GradleKtsProjectDirBuilder.Companion.`gradle kts project`
import java.io.File
import org.gradle.testkit.runner.GradleRunner


class KVisionExtensionTest : FunSpec({


    context("verify KVision extension") {

        context("verify extension and properties are available") {
            // quick smoke test

            val projectDir: File = `gradle kts project` {

                `build gradle kts`(
                    """
plugins {
    id("io.kvision")
}

kvision {
  enableGradleTasks.set(false)
  enableWebpackVersions.set(false)
  enableHiddenKotlinJsStore.set(false)
  enableSecureResolutions.set(false)
  enableBackendTasks.set(false)
  enableWorkerTasks.set(false)
  kotlinJsStoreDirectory.set(layout.projectDirectory.dir("another-directory"))

  versions {
    webpackDevServer.set("1.2.3")
    webpack.set("1.2.3")
    webpackCli.set("1.2.3")
    karma.set("1.2.3")
    mocha.set("1.2.3")
    async.set("1.2.3")
  }
}

""".trimIndent()
                )
            }
            val result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments(":tasks")
                .build()

            result.output shouldContain "BUILD SUCCESSFUL"
        }

        context("applied with alongside Kotlin/JS plugin") {
            val kvisionVersion = "5.10.1"
            val projectDir: File = `gradle kts project` {

                `settings gradle kts`(
                    """
                        rootProject.name = "kvision"
                    """.trimIndent()
                )

                `build gradle kts`(
                    """
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("io.kvision")
    kotlin("js") version "1.6.21"
}

repositories {
    mavenCentral()
}

val kotlinVersion: String = "1.6.21"
val kvisionVersion: String = "5.10.1"

val webDir = file("src/main/web")

kvision {
  enableGradleTasks.set(false)
  enableWebpackVersions.set(false)
  enableHiddenKotlinJsStore.set(false)
  enableSecureResolutions.set(false)
  enableBackendTasks.set(false)
  enableWorkerTasks.set(false)
  kotlinJsStoreDirectory.set(layout.projectDirectory.dir("another-directory"))

  versions {
    webpackDevServer.set("1.2.3")
    webpack.set("1.2.3")
    webpackCli.set("1.2.3")
    karma.set("1.2.3")
    mocha.set("1.2.3")
    async.set("1.2.3")
  }
}

kotlin {
    js {
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
                    static = mutableListOf("${'$'}buildDir/processedResources/js/main")
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
    sourceSets["main"].dependencies {
        implementation("io.kvision:kvision:$kvisionVersion")
        implementation("io.kvision:kvision-bootstrap:$kvisionVersion")
        implementation("io.kvision:kvision-bootstrap-css:$kvisionVersion")
        implementation("io.kvision:kvision-i18n:$kvisionVersion")
    }
    sourceSets["test"].dependencies {
        implementation(kotlin("test-js"))
        implementation("io.kvision:kvision-testutils:$kvisionVersion")
    }
    sourceSets["main"].resources.srcDir(webDir)
}
""".trimIndent()
                )
            }
            val result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments(":tasks")
                .build()

            result.output shouldContain "BUILD SUCCESSFUL"
            result.output shouldContain "Kvision tasks"
            result.output shouldContain "generatePotFile"
            result.output shouldContain "convertPoToJson"
            result.output shouldContain "zip"
        }

        context("with applied with alongside Kotlin/MPP plugin") {

            val projectDir: File = `gradle kts project` {
                val kvisionVersion = "5.10.1"
                `build gradle kts`(
                    """
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("io.kvision")
    kotlin("multiplatform") version "1.6.21"
}

repositories {
    mavenCentral()
}

version = "1.0.0-SNAPSHOT"
group = "com.example"

val webDir = file("src/frontendMain/web")

kvision {
  enableGradleTasks.set(false)
  enableWebpackVersions.set(false)
  enableHiddenKotlinJsStore.set(false)
  enableSecureResolutions.set(false)
  enableBackendTasks.set(false)
  enableWorkerTasks.set(false)
  kotlinJsStoreDirectory.set(layout.projectDirectory.dir("another-directory"))

  versions {
    webpackDevServer.set("1.2.3")
    webpack.set("1.2.3")
    webpackCli.set("1.2.3")
    karma.set("1.2.3")
    mocha.set("1.2.3")
    async.set("1.2.3")
  }
}

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
            }
        }
        val backendTest by getting {
            dependencies {
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
""".trimIndent()
                )
            }

            test("kotlin script") {
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":tasks")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
                result.output shouldContain "Kvision tasks"
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "workerBundle"
            }
        }
    }

})
