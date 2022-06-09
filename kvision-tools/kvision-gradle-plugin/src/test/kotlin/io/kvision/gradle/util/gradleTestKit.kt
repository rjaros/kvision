package io.kvision.gradle.util

import io.kotest.core.TestConfiguration
import io.kotest.engine.spec.tempdir
import java.io.File
import org.intellij.lang.annotations.Language


class GradleKtsProjectDirBuilder(
    private val projectDir : File
) {
    fun `build gradle kts`(@Language("kotlin") contents: String): File =
        projectDir.createFile("build.gradle.kts", contents)

    fun `settings gradle kts`(@Language("kotlin") contents: String): File =
        projectDir.createFile("settings.gradle.kts", contents)
}


fun TestConfiguration.`gradle kts project`(
    dir: File = tempdir(),
    build: GradleKtsProjectDirBuilder.() -> Unit,
): File {
    GradleKtsProjectDirBuilder(dir).apply(build)
    return dir
}


class GradleGroovyProjectDirBuilder(
    private val projectDir : File
) {
    fun `build gradle`(@Language("groovy") contents: String): File =
        projectDir. createFile("build.gradle", contents)

    fun `settings gradle`(@Language("groovy") contents: String): File =
        projectDir.createFile("settings.gradle", contents)

}


fun TestConfiguration.`gradle groovy project`(
    dir: File = tempdir(),
    build: GradleGroovyProjectDirBuilder.() -> Unit,
): File {
    GradleGroovyProjectDirBuilder(dir).apply(build)
    return dir
}


private fun File.createFile(filename: String, contents: String?): File =
    resolve(filename).apply {
        createNewFile()
        if (contents != null) {
            writeText(contents)
        }
    }
