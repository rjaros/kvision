plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
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

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from("$buildDir/dokka/html")
}

val sourcesJar by tasks.named("sourcesJar") {
    dependsOn("jsGenerateExternalsIntegrated")
}

val jsSourcesJar by tasks.named("jsSourcesJar") {
    dependsOn("jsGenerateExternalsIntegrated")
}

publishing {
    publications.withType<MavenPublication> {
        if (name == "kotlinMultiplatform") artifactId = "kvision-common-annotations"
        if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
        pom {
            defaultPom()
        }
    }
}

setupSigning()
setupPublication()
setupDokkaMpp()
