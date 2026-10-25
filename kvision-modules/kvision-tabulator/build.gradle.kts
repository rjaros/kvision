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
                api(project(":kvision-modules:kvision-state"))
                npm("tabulator-tables", libs.versions.tabulator.get())
            }
        }
        getByName("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(project(":kvision-modules:kvision-testutils"))
            }
        }
    }
}

setupDokka()
setupPublication()
