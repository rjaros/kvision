package io.kvision.gradle

import io.kotest.assertions.asClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.string.shouldContain
import java.io.File
import org.gradle.testkit.runner.GradleRunner
import org.intellij.lang.annotations.Language


class KVisionPluginTest : FunSpec({


    context("verify KVision plugin can be applied") {
        // simple smoke test

        test("groovy script") {

            val projectDir: File = tempdir().apply {

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
            val projectDir: File = tempdir().apply {

                `settings gradle kts`(
                    """
                        rootProject.name = "kvision"
                    """.trimIndent()
                )

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

        context("with applied with alongside Kotlin/JS plugin") {

            test("groovy script") {

                val projectDir: File = tempdir().apply {

                    `settings gradle`(
                        """
                            rootProject.name = 'hello-world'
                        """.trimIndent()
                    )

                    `build gradle`(
                        """
plugins {
    id 'io.kvision'
    id 'org.jetbrains.kotlin.multiplatform' version '1.6.21'
}

kotlin {
    js {
        browser { }
        binaries.executable()
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
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "workerBundle"
            }

            test("kotlin script") {
                val projectDir: File = tempdir().apply {

                    `settings gradle kts`(
                        """
                            rootProject.name = "kvision"
                        """.trimIndent()
                    )

                    `build gradle kts`(
                        """
                            plugins {
                                id("io.kvision")
                                kotlin("js") version "1.6.21"
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
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "workerBundle"
            }
        }
        context("with applied with alongside Kotlin/MPP plugin") {

            test("groovy script") {

                val projectDir: File = tempdir().apply {

                    `settings gradle`(
                        """
                            rootProject.name = 'hello-world'
                        """.trimIndent()
                    )

                    `build gradle`(
                        """
plugins {
    id 'io.kvision'
    id 'org.jetbrains.kotlin.multiplatform' version '1.6.21'
}

kotlin {
    js {
        browser { }
        binaries.executable()
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
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "workerBundle"
            }

            test("kotlin script") {
                val projectDir: File = tempdir().apply {

                    `settings gradle kts`(
                        """
                            rootProject.name = "kvision"
                        """.trimIndent()
                    )

                    val kvisionVersion: String = "5.10.1"
                    `build gradle kts`(
                        """
plugins {
    id("io.kvision")
    kotlin("multiplatform") version "1.6.21"
}

repositories {
    mavenCentral()
}

version = "1.0.0-SNAPSHOT"
group = "com.example"

val kotlinVersion: String = "1.6.21"

val webDir = file("src/frontendMain/web")

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

                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":tasks")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "workerBundle"
            }
        }
    }

    context("verify templates") {
        test("template") {
            File("../kvision-gradle-plugin-test/template").canonicalFile.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":tasks", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "workerBundle"
            }
        }

        test("template-fullstack-ktor") {
            File("../kvision-gradle-plugin-test/template-fullstack-ktor").canonicalFile.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":tasks", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "workerBundle"
            }
        }
    }
}) {

    companion object {

        fun File.`build gradle`(@Language("groovy") contents: String): File =
            createFile("build.gradle", contents)

        fun File.`settings gradle`(@Language("groovy") contents: String): File =
            createFile("settings.gradle", contents)

        fun File.`build gradle kts`(contents: String): File =
            createFile("build.gradle.kts", contents)

        fun File.`settings gradle kts`(@Language("kotlin") contents: String): File =
            createFile("settings.gradle.kts", contents)

        private fun File.createFile(filename: String, contents: String): File =
            resolve(filename).apply {
                createNewFile()
                writeText(contents)
            }
    }
}

/*
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("js") version kotlinVersion
    val kvisionVersion: String by System.getProperties()
    id("io.kvision") version kvisionVersion
}

version = "1.0.0-SNAPSHOT"
group = "com.example"

repositories {
    mavenCentral()
    jcenter()
    mavenLocal()
}

 */
