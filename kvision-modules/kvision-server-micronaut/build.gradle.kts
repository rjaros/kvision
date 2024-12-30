plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
    val kspVersion: String by System.getProperties()
    id("com.google.devtools.ksp") version kspVersion
}

// Versions
val kotlinVersion: String by System.getProperties()
val serializationVersion: String by project
val coroutinesVersion: String by project
val micronautVersion: String by project
val logbackVersion: String by project
val testNgVersion: String by project
val hamcrestVersion: String by project

kotlin {
    compilerOptions()
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
                implementation(kotlin("reflect"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$coroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")
                api(project.dependencies.platform("io.micronaut.platform:micronaut-platform:$micronautVersion"))
                api("io.micronaut:micronaut-inject")
                api("io.micronaut:micronaut-http")
                api("io.micronaut:micronaut-router")
                api("io.micronaut:micronaut-websocket")
                api("io.micronaut.reactor:micronaut-reactor")
                api("ch.qos.logback:logback-classic:$logbackVersion")
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

dependencies {
    add("kspJvm", platform("io.micronaut.platform:micronaut-platform:$micronautVersion"))
    add("kspJvm", "io.micronaut:micronaut-inject-kotlin")
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaGenerate")
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka/html"))

}

setupSigning()
setupPublication(true)
setupDokka()
