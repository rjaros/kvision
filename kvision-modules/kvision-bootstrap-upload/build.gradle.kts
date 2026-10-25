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
                api(project(":kvision-modules:kvision-common-types"))
                api(project(":kvision-modules:kvision-jquery"))
                api(project(":kvision-modules:kvision-bootstrap"))
                api(project(":kvision"))
                npm("bootstrap-fileinput", libs.versions.bootstrap.fileinput.get())
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
