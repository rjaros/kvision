plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("maven-publish")
    kotlin("kapt")
}

repositories()

// Versions
val kotlinVersion: String by System.getProperties()
val serializationVersion: String by project
val coroutinesVersion: String by project
val micronautVersion: String by project
val jacksonModuleKotlinVersion: String by project

kotlin {
    kotlinJsTargets()
    kotlinJvmTargets()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":kvision-modules:kvision-common-annotations"))
                api(project(":kvision-modules:kvision-common-types"))
                api(project(":kvision-modules:kvision-common-remote"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }
        val jsMain by getting {
            dependencies {
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk7"))
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")
                api(project.dependencies.enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
                api("io.micronaut:micronaut-inject")
                api("io.micronaut:micronaut-http")
                api("io.micronaut:micronaut-router")
                api("io.micronaut:micronaut-websocket")
                api("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
            }
        }
    }
}

publishing {
    publications.withType<MavenPublication> {
        if (name == "kotlinMultiplatform") artifactId = "kvision-server-micronaut"
        pom {
            defaultPom()
        }
    }
}

setupPublication()

kapt {
    arguments {
        arg("micronaut.processing.annotations", "pl.treksoft.kvision.remote")
        arg("micronaut.processing.group", "pl.treksoft")
        arg("micronaut.processing.module", "kvision-server-micronaut")
    }
}

dependencies {
    "kapt"(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    "kapt"("io.micronaut:micronaut-inject-java")
    "kapt"("io.micronaut:micronaut-validation")
    "kaptTest"("io.micronaut:micronaut-bom:$micronautVersion")
    "kaptTest"("io.micronaut:micronaut-inject-java")
}
