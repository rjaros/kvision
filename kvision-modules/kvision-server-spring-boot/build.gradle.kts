plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
}

// Versions
val kotlinVersion: String by System.getProperties()
val serializationVersion: String by project
val coroutinesVersion: String by project
val springBootVersion: String by project
val springDataRelationalVersion: String by project
val logbackVersion: String by project

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
                api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")
                api("org.springframework.boot:spring-boot-starter:$springBootVersion")
                api("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
                api("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
                api("org.springframework.data:spring-data-relational:$springDataRelationalVersion")
                api("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from("$buildDir/dokka/html")
}

publishing {
    publications.withType<MavenPublication> {
        if (name == "kotlinMultiplatform") artifactId = "kvision-server-spring-boot"
        if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
        pom {
            defaultPom()
        }
    }
}

setupSigning()
setupPublication()

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        configureEach {
            includes.from("../../Module.md")
            includeNonPublic.set(false)
        }
    }
}
