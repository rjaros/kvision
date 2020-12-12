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
    implementation(kotlin("test-js"))
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.test.get().kotlin)
}

val testLegacyJar by tasks.registering(Jar::class) {
    dependsOn("legacyTestClasses")
    archiveClassifier.set("tests")
    from(kotlin.js().compilations["test"].output.allOutputs)
}

val testIrJar by tasks.registering(Jar::class) {
    dependsOn("irTestClasses")
    archiveExtension.set("klib")
    archiveClassifier.set("tests")
    from(files("$buildDir/classes/kotlin/ir/test"))
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["testLegacyJar"]) {
                classifier = "tests"
            }
            artifact(tasks["testIrJar"]) {
                classifier = "tests"
            }
            pom {
                defaultPom()
            }
        }
    }
}

setupPublication()
