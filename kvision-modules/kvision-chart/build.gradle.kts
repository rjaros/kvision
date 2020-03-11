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
    implementation(npm("chart.js", "2.9.3"))
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

tasks {
    getByName("JsJar", Jar::class) {
        from("${rootProject.buildDir}/js/packages/kvision-${project.name}/package.json")
    }
}
