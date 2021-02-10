plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
}

val kotlinReactVersion: String by project

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    api("org.jetbrains:kotlin-react:$kotlinReactVersion")
    api("org.jetbrains:kotlin-react-dom:$kotlinReactVersion")
    implementation(npm("react", "17.0.1"))
    implementation(npm("react-dom", "17.0.1"))
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
