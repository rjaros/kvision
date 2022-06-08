package io.kvision.gradle

import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.string.shouldContain
import java.io.File
import org.gradle.testkit.runner.GradleRunner
import org.intellij.lang.annotations.Language


class KVisionPluginTest : FunSpec({


    context("verify plugin can be applied") {

        test("groovy script") {

            val projectDir: File = tempdir()

            projectDir.`settings gradle`(
                """
                    rootProject.name = 'hello-world'
                """.trimIndent()
            )
            projectDir.`build gradle`(
                """
                    plugins {
                        id 'io.kvision'
                    }
                """.trimIndent()
            )


            val result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .build()

            result.output shouldContain "BUILD SUCCESSFUL"
        }

        test("kotlin script") {
            val projectDir: File = tempdir()

            projectDir.`settings gradle kts`(
                """
                    rootProject.name = "kvision"
                """.trimIndent()
            )
            projectDir.`build gradle kts`(
                """
                    plugins {
                        id("io.kvision")
                    }
                """.trimIndent()
            )

            val result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .build()

            result.output shouldContain "BUILD SUCCESSFUL"
        }
    }

}) {

    companion object {

        fun File.`build gradle`(@Language("groovy") contents: String): File =
            createFile("build.gradle", contents)

        fun File.`settings gradle`(@Language("groovy") contents: String): File =
            createFile("settings.gradle", contents)

        fun File.`build gradle kts`(@Language("kotlin") contents: String): File =
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
