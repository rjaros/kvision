/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kvision.gradle

import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class KVisionGradleSubplugin : KotlinCompilerPluginSupportPlugin {

    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        groupId = "io.kvision",
        artifactId = "kvision-compiler-plugin",
        version = "5.9.0"
    )

    override fun apply(target: Project) = with(target) {
        target.rootProject.extensions.extraProperties.set(
            "nodeJsBinaryExecutable",
            getNodeJsBinaryExecutable(rootProject)
        )
        plugins.withId("org.jetbrains.kotlin.js") {
            afterEvaluate {
                if (project.findProperty("io.kvision.plugin.enableGradleTasks") != "false") {
                    tasks {
                        create("generatePotFile", Exec::class) {
                            group = "KVision"
                            description = "Generates pot file for translations"
                            dependsOn("compileKotlinJs")
                            executable = getNodeJsBinaryExecutable(rootProject)
                            args("${rootProject.buildDir}/js/node_modules/gettext-extract/bin/gettext-extract")
                            val kotlin =
                                this@with.extensions.getByName("kotlin") as org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
                            inputs.files(kotlin.sourceSets["main"].kotlin.files)
                            outputs.file("$projectDir/src/main/resources/i18n/messages.pot")
                        }
                        getByName("processResources", Copy::class) {
                            dependsOn("compileKotlinJs")
                            convertPOtoJSON(this@afterEvaluate, rootProject)
                        }
                        create("zip", Zip::class) {
                            dependsOn("browserProductionWebpack")
                            group = "package"
                            description = "Builds ZIP archive with the application"
                            destinationDirectory.set(file("$buildDir/libs"))
                            val distribution =
                                project.tasks.getByName(
                                    "browserProductionWebpack",
                                    KotlinWebpack::class
                                ).destinationDirectory
                            from(distribution) {
                                include("*.*")
                            }
                            val webDir = file("src/main/web")
                            from(webDir)
                            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                            inputs.files(distribution, webDir)
                            outputs.file(archiveFile)
                        }
                    }
                }
                if (rootProject.findProperty("io.kvision.plugin.enableWebpackVersions") != "false") {
                    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
                        versions.webpackDevServer.version = "4.8.1"
                        versions.webpack.version = "5.72.0"
                        versions.webpackCli.version = "4.9.2"
                        versions.karma.version = "6.3.18"
                        versions.mocha.version = "9.2.2"
                    }
                }
                if (rootProject.findProperty("io.kvision.plugin.enableHiddenKotlinJsStore") != "false") {
                    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension> {
                        lockFileDirectory = project.rootDir.resolve(".kotlin-js-store")
                    }
                }
                if (rootProject.findProperty("io.kvision.plugin.enableSecureResolutions") != "false") {
                    rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
                        rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
                            resolution("async", "^2.6.4")
                        }
                    }
                }
            }
        }
        plugins.withId("org.jetbrains.kotlin.multiplatform") {
            afterEvaluate {
                if (project.findProperty("io.kvision.plugin.enableGradleTasks") != "false") {
                    tasks {
                        create("generatePotFile", Exec::class) {
                            group = "KVision"
                            description = "Generates pot file for translations"
                            dependsOn("compileKotlinFrontend")
                            executable = getNodeJsBinaryExecutable(rootProject)
                            args("${rootProject.buildDir}/js/node_modules/gettext-extract/bin/gettext-extract")
                            val kotlin =
                                this@with.extensions.getByName("kotlin") as org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
                            inputs.files(kotlin.sourceSets["frontendMain"].kotlin.files)
                            outputs.file("$projectDir/src/frontendMain/resources/i18n/messages.pot")
                        }
                        getByName("frontendProcessResources", Copy::class) {
                            dependsOn("compileKotlinFrontend")
                            convertPOtoJSON(this@afterEvaluate, rootProject)
                        }
                        if (project.findProperty("io.kvision.plugin.enableBackendTasks") != "false") {
                            getByName("compileKotlinBackend") {
                                dependsOn("compileCommonMainKotlinMetadata")
                            }
                        }
                        getByName("compileKotlinFrontend") {
                            dependsOn("compileCommonMainKotlinMetadata")
                        }
                        create("generateKVisionSources") {
                            group = "KVision"
                            description = "Generates KVision sources for fullstack interfaces"
                            dependsOn("compileCommonMainKotlinMetadata")
                        }
                        if (project.findProperty("io.kvision.plugin.enableWorkerTasks") == "true") {
                            create("workerBundle") {
                                group = "KVision"
                                description = "Builds and copies webworker bundle to the frontend static resources"
                                dependsOn("workerBrowserProductionWebpack")
                                inputs.dir("$projectDir/src/workerMain/kotlin")
                                outputs.files("$buildDir/processedResources/frontend/main/worker.js")
                                doLast {
                                    exec {
                                        executable = getNodeJsBinaryExecutable(rootProject)
                                        workingDir =
                                            file("${rootProject.buildDir}/js/packages/${rootProject.name}-worker")
                                        args(
                                            "${rootProject.buildDir}/js/node_modules/webpack/bin/webpack.js",
                                            "--config",
                                            "${rootProject.buildDir}/js/packages/${rootProject.name}-worker/webpack.config.js"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                if (rootProject.findProperty("io.kvision.plugin.enableWebpackVersions") != "false") {
                    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
                        versions.webpackDevServer.version = "4.8.1"
                        versions.webpack.version = "5.72.0"
                        versions.webpackCli.version = "4.9.2"
                        versions.karma.version = "6.3.18"
                        versions.mocha.version = "9.2.2"
                    }
                }
                if (rootProject.findProperty("io.kvision.plugin.enableHiddenKotlinJsStore") != "false") {
                    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension> {
                        lockFileDirectory = project.rootDir.resolve(".kotlin-js-store")
                    }
                }
                if (rootProject.findProperty("io.kvision.plugin.enableSecureResolutions") != "false") {
                    rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
                        rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
                            resolution("async", "^2.6.4")
                        }
                    }
                }
            }
        }
    }

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        return project.provider {
            val options = mutableListOf<SubpluginOption>()
            options
        }
    }

    override fun getCompilerPluginId(): String = "KVisionPlugin"

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
        return true
    }

    private fun Copy.convertPOtoJSON(project: Project, rootProject: Project) {
        exclude("**/*.pot")
        doLast("Convert PO to JSON") {
            destinationDir.walkTopDown().filter {
                it.isFile && it.extension == "po"
            }.forEach {
                project.exec {
                    executable = getNodeJsBinaryExecutable(rootProject)
                    args(
                        "${rootProject.buildDir}/js/node_modules/@rjaros/gettext.js/bin/po2json",
                        it.absolutePath,
                        "${it.parent}/${it.nameWithoutExtension}.json"
                    )
                    println("Converted ${it.name} to ${it.nameWithoutExtension}.json")
                }
                it.delete()
            }
        }
    }

    private fun getNodeJsBinaryExecutable(rootProject: Project): String {
        val nodeDir = NodeJsRootPlugin.apply(rootProject).nodeJsSetupTaskProvider.get().destination
        val isWindows = System.getProperty("os.name").toLowerCase().contains("windows")
        val nodeBinDir = if (isWindows) nodeDir else nodeDir.resolve("bin")
        val command = NodeJsRootPlugin.apply(rootProject).nodeCommand
        val finalCommand = if (isWindows && command == "node") "node.exe" else command
        return nodeBinDir.resolve(finalCommand).absolutePath
    }
}
