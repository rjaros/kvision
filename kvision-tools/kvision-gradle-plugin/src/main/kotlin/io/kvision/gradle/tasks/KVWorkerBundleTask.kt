package io.kvision.gradle.tasks

import io.kvision.gradle.KVisionPlugin
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile

abstract class KVWorkerBundleTask : Exec(), KVisionTask {

    @get:Input
    abstract val nodeJsBin: Property<String>

    @get:InputFile
    abstract val webpackJs: RegularFileProperty

    @get:OutputFile
    abstract val webpackConfigJs: RegularFileProperty

    @get:InputDirectory
    abstract val workerMainSrcDir: DirectoryProperty

    @get:OutputFile
    abstract val workerJsFile: RegularFileProperty

    init {
        group = KVisionPlugin.KVISION_TASK_GROUP
        description = "Builds and copies webworker bundle to the frontend static resources"
    }

}
