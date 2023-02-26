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
    api("io.github.copper-leaf:ballast-navigation:$ballastVersion")
    testImplementation(kotlin("test-js"))
}

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn("generateExternalsIntegrated")
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
        }
    }
}

setupSigning()
setupPublication()
setupDokka()
