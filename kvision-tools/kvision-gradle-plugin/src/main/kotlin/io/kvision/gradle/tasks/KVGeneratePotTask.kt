package io.kvision.gradle.tasks

import io.kvision.gradle.KVisionPlugin
import io.kvision.gradle.execCapture
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.logging.LogLevel
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

abstract class KVGeneratePotTask @Inject constructor(
    private val executor: ExecOperations
) : DefaultTask(), KVisionTask {

    @get:Input
    abstract val nodeJsBinary: Property<String>

    @get:InputFile
    abstract val getTextExtractBin: RegularFileProperty

    @get:InputFile
    @get:Optional
    abstract val getTextConfigJson: RegularFileProperty

    @get:OutputFile
    abstract val potFile: RegularFileProperty

    init {
        group = KVisionPlugin.KVISION_TASK_GROUP
        description = "Generates pot file for translations"
    }

    @TaskAction
    fun generate() {
        val potFile: String = potFile.asFile.get().canonicalPath
        val getTextConfigJson: String? = getTextConfigJson.asFile.orNull?.canonicalPath
        val nodeJsBinary: String = nodeJsBinary.get()
        val getTextExtractBin: String = getTextExtractBin.get().asFile.canonicalPath

        val execResult = executor.execCapture {
            executable(nodeJsBinary)
            args(
                getTextExtractBin,
                "--output $potFile",
            )

            if (getTextConfigJson != null) {
                args("--config $getTextConfigJson")
            }
        }

        logger.log(
            if (execResult.isSuccess) LogLevel.LIFECYCLE else LogLevel.ERROR,
            "gettext-extract [exitcode:${execResult.exitValue}]\n${execResult.output}",
        )

        execResult.rethrowFailure()
    }
}
