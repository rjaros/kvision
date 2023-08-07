plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

// Versions
val kotlinVersion: String by System.getProperties()
val serializationVersion: String by project
val coroutinesVersion: String by project
val javalinVersion: String by project
val guiceVersion: String by project
val jacksonModuleKotlinVersion: String by project
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
                implementation(kotlin("reflect"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")
                api("io.javalin:javalin:$javalinVersion")
                api("com.google.inject:guice:$guiceVersion")
                api("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
                api("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka/html"))

}

setupSigning()
setupPublication(true)
setupDokkaMpp(withJvm = true)
