plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val onsenuiVersion: String by project

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    implementation(npm("onsenui", "^$onsenuiVersion"))
    testImplementation(kotlin("test-js"))
    testImplementation(project(":kvision-modules:kvision-testutils"))
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
