package io.kvision.gradle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kvision.gradle.util.GradleKtsProjectDirBuilder.Companion.`gradle kts project`
import java.io.File
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome


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
  enableWorkerTasks.set(false)
  kotlinJsStoreDirectory.set(layout.projectDirectory.dir("another-directory"))

  versions {
    webpackDevServer.set("1.2.3")
    webpack.set("1.2.3")
    webpackCli.set("1.2.3")
    karma.set("1.2.3")
    mocha.set("1.2.3")
  }
}
""".trimIndent()
                )
            }

            test("can list tasks") {
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":tasks", "--info", "--stacktrace")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
        }

        context("applied with alongside Kotlin/JS plugin") {
            val projectDir: File = `gradle kts project` {
                `build gradle kts`(
                    """
$baseKotlinJsBuildGradleKts

kvision {
  versions {
    karma.set("2.2.2")
    mocha.set("3.3.3")
    webpack.set("4.4.4")
    webpackCli.set("5.5.5")
    webpackDevServer.set("6.6.6")
  }
}

tasks.register("printNodeJsVersions") {
    doLast {
        val versions = rootProject
            .the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>()
            .versions
            .run {
                listOf(
                    webpackDevServer,
                    webpack,
                    webpackCli,
                    karma,
                    mocha,
                )
            }
        logger.info(versions.joinToString("\n"))
    }
}

tasks.register("printYarnVersions") {
    doLast {
        val versions = rootProject
            .the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>()
            .resolutions
            .joinToString("\n") {
                it.path + it.includedVersions + it.excludedVersions
            }
        logger.info(versions)
    }
}
""".trimIndent()
                )
            }

            test("verify tasks can be listed") {
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":tasks", "--info", "--stacktrace")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
                result.output shouldContain "Kvision tasks"
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "zip"

                result.task(":tasks").shouldNotBeNull()
                    .outcome
                    .shouldBe(TaskOutcome.SUCCESS)
            }

            context("verify NodeJS versions are overridden") {

                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":printNodeJsVersions", "--info", "--stacktrace")
                    .build()

                test("verify :printNodeJsVersions runs successfully") {
                    result.output shouldContain "BUILD SUCCESSFUL"

                    result.task(":printNodeJsVersions").shouldNotBeNull()
                        .outcome
                        .shouldBe(TaskOutcome.SUCCESS)
                }

                test("expect karma version is overridden") {
                    result.output shouldContain "NpmPackageVersion(name=karma, version=2.2.2)"
                }
                test("expect mocha version is overridden") {
                    result.output shouldContain "NpmPackageVersion(name=mocha, version=3.3.3)"
                }
                test("expect webpack version is overridden") {
                    result.output shouldContain "NpmPackageVersion(name=webpack, version=4.4.4)"
                }
                test("expect webpack-cli version is overridden") {
                    result.output shouldContain "NpmPackageVersion(name=webpack-cli, version=5.5.5)"
                }
                test("expect webpack-dev-server version is overridden") {
                    result.output shouldContain "NpmPackageVersion(name=webpack-dev-server, version=6.6.6)"
                }
            }

/*            context("verify Yarn versions are overridden") {

                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":printYarnVersions", "--info", "--stacktrace")
                    .build()

                test("verify :printYarnVersions runs successfully") {
                    result.output shouldContain "BUILD SUCCESSFUL"

                    result.task(":printYarnVersions").shouldNotBeNull()
                        .outcome
                        .shouldBe(TaskOutcome.SUCCESS)
                }
                test("expect async version is overridden") {
                    result.output shouldContain "async[1.1.1][]"
                }
            }*/
        }

        context("with applied with alongside Kotlin/MPP plugin") {

            val projectDir: File = `gradle kts project` {
                val kvisionVersion = "5.18.0"
                `build gradle kts`(
                    """
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("io.kvision")
    kotlin("multiplatform") version "1.8.0-Beta"
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
  enableWorkerTasks.set(false)
  kotlinJsStoreDirectory.set(layout.projectDirectory.dir("another-directory"))

  versions {
    webpackDevServer.set("1.2.3")
    webpack.set("1.2.3")
    webpackCli.set("1.2.3")
    karma.set("1.2.3")
    mocha.set("1.2.3")
  }
}

kotlin {
    jvm("backend") {
        compilations.all {
            java {
                targetCompatibility = JavaVersion.VERSION_17
            }
            kotlinOptions {
                jvmTarget = "17"
                freeCompilerArgs = listOf("-Xjsr305=strict")
            }
        }
    }
    js("frontend", IR) {
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
                    .withArguments(":tasks", "--info", "--stacktrace")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
                result.output shouldContain "Kvision tasks"
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"

                result.task(":tasks").shouldNotBeNull()
                    .outcome
                    .shouldBe(TaskOutcome.SUCCESS)
            }
        }
    }

}) {
    companion object {

        private val baseKotlinJsBuildGradleKts: String = run {
            val kvisionVersion = "5.18.0"
            //language=kotlin
            """
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("io.kvision")
    kotlin("js") version "1.8.0-Beta"
}

repositories {
    mavenCentral()
}

val kotlinVersion: String = "1.8.0-Beta"
val kvisionVersion: String = "5.18.0"

val webDir = file("src/main/web")

kotlin {
    js(IR) {
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
"""
        }

    }
}
