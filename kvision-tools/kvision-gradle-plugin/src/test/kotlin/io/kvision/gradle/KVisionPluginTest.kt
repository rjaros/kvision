package io.kvision.gradle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import io.kvision.gradle.util.GradleGroovyProjectDirBuilder.Companion.`gradle groovy project`
import io.kvision.gradle.util.GradleKtsProjectDirBuilder.Companion.`gradle kts project`
import java.io.File
import org.gradle.testkit.runner.GradleRunner


class KVisionPluginTest : FunSpec({


    context("verify KVision plugin can be applied") {
        // simple smoke test

        test("groovy script") {

            val projectDir: File = `gradle groovy project` {

                `settings gradle`(
                    """
                        rootProject.name = 'hello-world'
                    """.trimIndent()
                )

                `build gradle`(
                    """
                        plugins {
                            id 'io.kvision'
                        }
                    """.trimIndent()
                )
            }

            val result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .build()

            result.output shouldContain "BUILD SUCCESSFUL"
        }

        test("kotlin script") {
            val projectDir: File = `gradle kts project` {
                `build gradle kts`(
                    """
                        plugins {
                            id("io.kvision")
                        }
                    """.trimIndent()
                )
            }

            val result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .build()

            result.output shouldContain "BUILD SUCCESSFUL"
        }
    }

    context("verify KVision plugin creates tasks") {

        context("applied with alongside Kotlin/JS plugin") {
            val kvisionVersion = "5.15.2"
            val projectDir: File = `gradle kts project` {
                `build gradle kts`(
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
""".trimIndent()
                )
            }

            test("expect listed tasks contain KVision tasks") {

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
            }

            test("expect generatePotFile task runs successfully") {

                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":generatePotFile", "--info", "--stacktrace")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
            test("expect convertPoToJson task runs successfully") {

                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":convertPoToJson", "--info", "--stacktrace")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
            test("expect zip task task runs successfully") {

                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":zip", "--info", "--stacktrace")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
        }
        context("with applied with alongside Kotlin/MPP plugin") {


            test("kotlin script") {
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

val kotlinVersion: String = "1.8.0-Beta"

val webDir = file("src/frontendMain/web")

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

                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":tasks", "--info", "--stacktrace")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
                result.output shouldContain "Kvision tasks"
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
            }
        }
    }
})
