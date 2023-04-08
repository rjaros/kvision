plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val ballastVersion: String by project

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    api(project(":kvision-modules:kvision-state-flow"))
    api("io.github.copper-leaf:ballast-core:$ballastVersion")
    api("io.github.copper-leaf:ballast-saved-state:$ballastVersion")
    api("io.github.copper-leaf:ballast-repository:$ballastVersion")
    api("io.github.copper-leaf:ballast-sync:$ballastVersion")
    api("io.github.copper-leaf:ballast-undo:$ballastVersion")
    testImplementation(kotlin("test-js"))
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
            if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
        }
    }
}

setupSigning()
setupPublication()
setupDokka()
