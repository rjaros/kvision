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
                npm("leaflet", libs.versions.leaflet.get())
                npm("geojson", libs.versions.geojson.get())
            }
        }
        getByName("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(project(":kvision-modules:kvision-testutils"))
                implementation(project.dependencies.platform("io.kotest:kotest-bom:${libs.versions.kotest.get()}"))
                implementation("io.kotest:kotest-assertions-core")
            }
        }
    }
}

setupDokka()
setupPublication()
