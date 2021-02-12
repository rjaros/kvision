plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
}

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    implementation(npm("onsenui", "^2.11.2"))
    testImplementation(kotlin("test-js"))
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
