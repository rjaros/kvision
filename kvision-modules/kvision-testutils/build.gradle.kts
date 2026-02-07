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
        val jsMain by getting {
            dependencies {
                api(project(":kvision"))
                api(project(":kvision-modules:kvision-jquery"))
                api(kotlin("test-js"))

                implementation(project.dependencies.platform("io.kotest:kotest-bom:${libs.versions.kotest.get()}")) {
                    because("to use the latest stable version of Kotest")
                }
                implementation("io.kotest:kotest-assertions-core") {
                    because("improved test assertions")
                }
            }
        }
    }
}

setupDokka(tasks.dokkaGeneratePublicationHtml)
setupPublication()
