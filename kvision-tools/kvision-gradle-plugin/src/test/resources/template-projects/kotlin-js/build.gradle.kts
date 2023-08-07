import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

// based on https://github.com/rjaros/kvision-examples/tree/master/template

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("js") version kotlinVersion
//    val kvisionVersion: String by System.getProperties()
    id("io.kvision") // version kvisionVersion
    // note: the KVision plugin version is removed because the plugin is loaded from the test
    // class path, thanks to Gradle TestKit
}

version = "1.0.0-SNAPSHOT"
group = "com.example"

repositories {
    mavenCentral()
}

// Versions
val kotlinVersion: String by System.getProperties()
val kvisionVersion: String by System.getProperties()

val webDir = file("src/main/web")

kotlin {
    js(IR) {
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
                    static = mutableListOf("${buildDir}/processedResources/js/main")
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
    sourceSets["main"].dependencies {
        implementation("io.kvision:kvision:$kvisionVersion")
        implementation("io.kvision:kvision-bootstrap:$kvisionVersion")
        implementation("io.kvision:kvision-i18n:$kvisionVersion")
    }
    sourceSets["test"].dependencies {
        implementation(kotlin("test-js"))
        implementation("io.kvision:kvision-testutils:$kvisionVersion")
    }
    sourceSets["main"].resources.srcDir(webDir)
}
