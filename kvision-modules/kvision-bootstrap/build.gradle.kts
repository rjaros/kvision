plugins {
    kotlin("js")
    id("maven-publish")
}

repositories()

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    implementation(npm("popper.js", "^1.16.1"))
    implementation(npm("bootstrap", "^4.5.3"))
    implementation(npm("awesome-bootstrap-checkbox", "^1.0.1"))
    implementation(npm("element-resize-event", "^3.0.3"))
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

setupPublication()
