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
    testImplementation(kotlin("test-js"))
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.test.get().kotlin)
}

val testJar by tasks.registering(Jar::class) {
    dependsOn("testClasses")
    archiveClassifier.set("tests")
    from(kotlin.js().compilations["test"].output.allOutputs)
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["testJar"]) {
                classifier = "tests"
            }
            pom {
                defaultPom()
            }
        }
    }
}

setupPublication()
