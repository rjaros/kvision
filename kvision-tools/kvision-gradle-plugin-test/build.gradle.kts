import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import io.kvision.gradle.kvision

plugins {
    kotlin("js") version "1.6.21"
    id("io.kvision") version "5.10.0"
}

kotlin {
    js {
        browser()
    }
}

kvision {
    enableHiddenKotlinJsStore.set(false)
}


// Versions
val kotlinVersion: String = "1.6.21"
val kvisionVersion: String = "5.10.0"

val webDir = file("src/main/web")

kotlin {
    js {
        browser {
            runTask {
                outputFileName = "main.bundle.js"
                sourceMaps = false
                devServer = KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 3000,
                    proxy = mutableMapOf(
                        "/kv/*" to "http://localhost:8080",
                        "/kvws/*" to mapOf("target" to "ws://localhost:8080", "ws" to true)
                    ),
                    static = mutableListOf("$buildDir/processedResources/js/main")
                )
            }
            webpackTask {
                outputFileName = "main.bundle.js"
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val main by getting {
            dependencies {
                implementation(dependencies.platform(kotlin("bom")))

                implementation(kvision("kvision", kvisionVersion))
                implementation(kvision("kvision-bootstrap", kvisionVersion))
                implementation(kvision("kvision-bootstrap-css", kvisionVersion))
                implementation(kvision("kvision-i18n", kvisionVersion))


                implementation(dependencies.platform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:0.0.1-pre.334"))

            }
            resources.srcDir(webDir)
        }

        val test by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(kvision("kvision-testutils", kvisionVersion))
            }
        }
    }
}
