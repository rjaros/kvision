package io.kvision.gradle

import io.kotest.assertions.asClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import java.io.File
import org.gradle.testkit.runner.GradleRunner


class MultiplatformTemplateTest : FunSpec({
    val templateProjectDir =
        this::class.java.getResource("/template-projects/template-fullstack-ktor")
            ?.toURI()
            ?.let { File(it) }
            ?: error("could not load template project")

    context("verify template-fullstack-ktor") {
        test("verify tasks list contains expected KVision tasks") {

            templateProjectDir.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":tasks", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
                result.output shouldContain "Kvision tasks"
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "workerBundle"
            }
        }

        test("verify generatePotFile task") {
            templateProjectDir.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":generatePotFile", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
        }

        test("verify convertPoToJson task") {
            templateProjectDir.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":convertPoToJson", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
        }

        test("verify workerBundle task") {
            templateProjectDir.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":workerBundle", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
        }
    }
})
