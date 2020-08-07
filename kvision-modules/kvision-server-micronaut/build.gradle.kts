buildscript {
    extra.set("production", (findProperty("prod") ?: findProperty("production") ?: "false") == "true")
}

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
                implementation(kotlin("stdlib-common"))
                api(project(":kvision-modules:kvision-common-annotations"))
                api(project(":kvision-modules:kvision-common-types"))
                api(project(":kvision-modules:kvision-common-remote"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serializationVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
            }
        }
        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("stdlib-jdk7"))
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
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
