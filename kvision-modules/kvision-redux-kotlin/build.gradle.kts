plugins {
    kotlin("js")
    id("maven-publish")
}

repositories()

// Versions
val reduxKotlinVersion: String by project
val reduxKotlinThunkVersion: String by project

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    api("org.reduxkotlin:redux-kotlin-js:$reduxKotlinVersion")
    api("org.reduxkotlin:redux-kotlin-thunk-js:$reduxKotlinThunkVersion")
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

setupPublication()
