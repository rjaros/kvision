plugins {
    kotlin("js")
    id("kotlinx-serialization")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
}

val coroutinesVersion: String by project

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(project(":kvision-modules:kvision-common-types"))
    api(rootProject)
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
    implementation(npm("bootstrap-fileinput", "^5.1.3"))
    testImplementation(kotlin("test-js"))
    testImplementation(project(":kvision-modules:kvision-testutils"))
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.main.get().kotlin)
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            pom {
                defaultPom()
            }
        }
    }
}

setupSigning()
setupPublication()
