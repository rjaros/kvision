package io.kvision.gradle

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject


/**
 * Configuration for the KVision plugin.
 *
 * The default values are set in [KVisionPlugin.createKVisionExtension].
 */
abstract class KVisionExtension @Inject constructor(
    private val objects: ObjectFactory,
    private val providers: ProviderFactory,
) {

    val enableGradleTasks: Property<Boolean> =
        kvisionGradleProperty("enableGradleTasks")

    val enableHiddenKotlinJsStore: Property<Boolean> =
        kvisionGradleProperty("enableHiddenKotlinJsStore")

    val enableResolutions: Property<Boolean> =
        kvisionGradleProperty("enableResolutions")

    val enableWorkerTasks: Property<Boolean> =
        kvisionGradleProperty("enableWorkerTasks", false)

    val enableKsp: Property<Boolean> =
        kvisionGradleProperty("enableKsp")

    /** The location of generated resources that will be included in the packaged frontend. */
    abstract val generatedFrontendResources: DirectoryProperty

    private fun kvisionGradleProperty(
        property: String,
        default: Boolean = true,
    ): Property<Boolean> {
        val convention = providers.gradleProperty("io.kvision.plugin.$property")
            .map { it.toBoolean() }
            .orElse(default)
        return objects.property<Boolean>().convention(convention)
    }

    abstract val kotlinJsStoreDirectory: DirectoryProperty

    /**
     * Set the Node binary used to execute KVision tasks. The default uses the Node binary
     * installed the Kotlin JS/MPP Gradle plugin.
     */
    abstract val nodeBinaryPath: Property<String>

}
