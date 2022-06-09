package io.kvision.gradle

import io.kotest.assertions.asClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import java.io.File
import org.gradle.testkit.runner.GradleRunner


class JsTemplateTest : FunSpec({
    val templateProjectDir = this::class.java.getResource("/template-projects/kotlin-js")
        ?.toURI()
        ?.let { File(it) }
        ?: error("could not load template project")

    context("verify JS template") {

        test("expect task list contains KVision tasks") {
            templateProjectDir.canonicalFile.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":tasks", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
                result.output shouldContain "Kvision tasks"
                result.output shouldContain "generatePotFile"
                result.output shouldContain "convertPoToJson"
                result.output shouldContain "zip"
            }
        }

        test("verify generatePotFile task runs successfully") {
            templateProjectDir.canonicalFile.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":generatePotFile", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
        }

        test("verify convertPoToJson task runs successfully") {
            templateProjectDir.canonicalFile.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":convertPoToJson", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
        }

        test("verify zip task runs successfully") {
            templateProjectDir.canonicalFile.asClue { projectDir ->
                val result = GradleRunner.create()
                    .withProjectDir(projectDir)
                    .withPluginClasspath()
                    .withArguments(":zip", "--stacktrace", "--info")
                    .build()

                result.output shouldContain "BUILD SUCCESSFUL"
            }
        }
    }
})
