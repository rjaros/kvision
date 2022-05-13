package io.kvision.gradle


import org.gradle.api.artifacts.ExternalModuleDependency
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler


private const val KVISION_GROUP = "io.kvision"


fun KotlinDependencyHandler.kvision(
    module: String,
    version: String? = null,
): ExternalModuleDependency =
    project.dependencies.create(
        when (version) {
            null -> "$KVISION_GROUP:$module"
            else -> "$KVISION_GROUP:$module:$version"
        }
    ) as ExternalModuleDependency
