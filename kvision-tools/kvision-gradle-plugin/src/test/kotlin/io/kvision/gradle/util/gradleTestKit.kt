package io.kvision.gradle.util

import io.kotest.core.TestConfiguration
import io.kotest.engine.spec.tempdir
import java.io.File
import org.intellij.lang.annotations.Language


class GradleKtsProjectDirBuilder {

    @Language("kotlin")
    private var settingsGradleKts: String = """
        rootProject.name = "kvision-gradle-plugin-test"
    """.trimIndent()

    @Language("kotlin")
    private var buildGradleKts: String = ""

    fun `build gradle kts`(@Language("kotlin") contents: String) {
        buildGradleKts = contents
    }

    fun `settings gradle kts`(@Language("kotlin") contents: String) {
        settingsGradleKts = contents
    }

    companion object {

        fun TestConfiguration.`gradle kts project`(
            projectDir: File = tempdir(),
            build: GradleKtsProjectDirBuilder.() -> Unit,
        ): File {
            val project = GradleKtsProjectDirBuilder().apply(build)

            return projectDir.apply {
                createFile("build.gradle.kts", project.buildGradleKts)
                createFile("settings.gradle.kts", project.settingsGradleKts)
            }
        }
    }
}


class GradleGroovyProjectDirBuilder {

    @Language("groovy")
    private var settingsGradle: String = """
        rootProject.name = 'hello-world'
    """.trimIndent()

    @Language("groovy")
    private var buildGradle: String = ""

    fun `build gradle`(@Language("groovy") contents: String) {
        buildGradle = contents
    }

    fun `settings gradle`(@Language("groovy") contents: String) {
        settingsGradle = contents
    }

    companion object {

        fun TestConfiguration.`gradle groovy project`(
            projectDir: File = tempdir(),
            build: GradleGroovyProjectDirBuilder.() -> Unit,
        ): File {
            val project = GradleGroovyProjectDirBuilder().apply(build)

            return projectDir.apply {
                createFile("build.gradle", project.buildGradle)
                createFile("settings.gradle", project.settingsGradle)
            }
        }
    }
}


private fun File.createFile(filename: String, contents: String?): File =
    resolve(filename).apply {
        createNewFile()
        if (contents != null) {
            writeText(contents)
        }
    }
