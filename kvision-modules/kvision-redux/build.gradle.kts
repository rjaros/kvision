plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
}

val kotlinReduxVersion: String by project

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    api("org.jetbrains:kotlin-redux:$kotlinReduxVersion") {
        exclude("org.jetbrains.kotlinx", "kotlinx-html-js")
    }
    implementation(npm("redux", "^4.0.5"))
    implementation(npm("redux-thunk", "^2.3.0"))
    testImplementation(kotlin("test-js"))
    testImplementation(project(":kvision-modules:kvision-testutils"))
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.main.get().kotlin)
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from("$buildDir/dokka/html")
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
            pom {
                defaultPom()
            }
        }
    }
}

setupSigning()
setupPublication()

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        configureEach {
            includes.from("../../Module.md")
            includeNonPublic.set(false)
        }
    }
}
