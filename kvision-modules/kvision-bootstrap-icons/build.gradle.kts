plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.nmcp)
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

kotlin {
    compilerOptions()
    kotlinJsTargets()
    sourceSets {
        getByName("jsMain") {
            dependencies {
                api(project(":kvision"))
                npm("bootstrap-icons", libs.versions.bootstrap.icons.get())
            }
        }
        getByName("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

setupDokka()
setupPublication()
