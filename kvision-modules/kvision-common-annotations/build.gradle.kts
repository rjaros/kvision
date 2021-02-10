plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
}

// Versions
val serializationVersion: String by project

kotlin {
    kotlinJsTargets()
    kotlinJvmTargets()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        val jsMain by getting {
            dependencies {
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
    }
}

publishing {
    publications.withType<MavenPublication> {
        if (name == "kotlinMultiplatform") artifactId = "kvision-common-annotations"
        pom {
            defaultPom()
        }
    }
}

setupSigning()
setupPublication()
