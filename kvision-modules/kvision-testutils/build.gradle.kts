buildscript {
    extra.set("production", (findProperty("prod") ?: findProperty("production") ?: "false") == "true")
}

plugins {
    kotlin("js")
    id("maven-publish")
}

repositories()

kotlin {
    kotlinJsTargets()
}

dependencies {
    implementation(kotlin("stdlib-js"))
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
    from(tasks["compileTestKotlinJs"].outputs)
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
