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
                npm("@popperjs/core", libs.versions.popperjs.core.get())
                npm("bootstrap", libs.versions.bootstrap.asProvider().get())
            }
        }
        getByName("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(project(":kvision-modules:kvision-testutils"))
                implementation(project(":kvision-modules:kvision-jquery"))
            }
        }
    }
}

setupDokka()
setupPublication()
