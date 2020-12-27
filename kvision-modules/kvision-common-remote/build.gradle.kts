plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("maven-publish")
}

repositories()

// Versions
val serializationVersion: String by project
val coroutinesVersion: String by project
val jqueryKotlinVersion: String by project
val testNgVersion: String by project
val hamcrestVersion: String by project

kotlin {
    kotlinJsTargets()
    kotlinJvmTargets()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("pl.treksoft:jquery-kotlin:$jqueryKotlinVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                api(project(":kvision-modules:kvision-common-types"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.testng:testng:$testNgVersion")
                implementation("org.hamcrest:hamcrest:$hamcrestVersion")
            }
        }
    }
}

tasks.withType<Test> {
    useTestNG()
}

publishing {
    publications.withType<MavenPublication> {
        if (name == "kotlinMultiplatform") artifactId = "kvision-common-remote"
        pom {
            defaultPom()
        }
    }
}

setupPublication()
