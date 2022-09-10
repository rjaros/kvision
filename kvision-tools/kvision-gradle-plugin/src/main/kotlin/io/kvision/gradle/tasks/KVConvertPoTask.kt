package io.kvision.gradle.tasks

import io.kvision.gradle.KVisionPlugin
import io.kvision.gradle.execCapture
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

abstract class KVConvertPoTask @Inject constructor(
    private val executor: ExecOperations,
) : DefaultTask(), KVisionTask {

    @get:InputDirectory
    @get:SkipWhenEmpty
    abstract val sourceDirectory: DirectoryProperty

    @get:InputFile
    abstract val po2jsonBinDir: RegularFileProperty

    @get:Input
    abstract val nodeJsBinary: Property<String>

    @get:OutputDirectory
    abstract val destinationDirectory: DirectoryProperty

    init {
        group = KVisionPlugin.KVISION_TASK_GROUP
    }

    @TaskAction
    fun convert() {
        val po2jsonBinDir: String = po2jsonBinDir.get().asFile.canonicalPath
        val nodeJsBinary: String = nodeJsBinary.get()

        sourceDirectory.asFileTree.filter {
            it.isFile && it.extension == "po"
        }.onEach { poFile ->
            val result = executor.execCapture {
                executable(nodeJsBinary)
                args(
                    po2jsonBinDir,
                    poFile.absolutePath,
                    "${destinationDirectory.get().asFile}/${poFile.nameWithoutExtension}.json"
                )
                logger.info("Converted ${poFile.name} to ${poFile.nameWithoutExtension}.json")
            }

            if (!result.isSuccess) {
                logger.error(result.output)
                result.rethrowFailure()
            }
        }
    }
}
