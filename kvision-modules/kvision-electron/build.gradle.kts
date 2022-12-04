plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
}

val kotlinNodeVersion: String by project
val electronVersion: String by project
val electronRemoteVersion: String by project

kotlin {
    js(IR) {
        compilations.all {
            kotlinOptions {
                moduleKind = "umd"
            }
        }
        nodejs {
            testTask {
                useKarma()
            }
        }
    }
}

dependencies {
    api(rootProject)
    api("org.jetbrains.kotlin-wrappers:kotlin-node:$kotlinNodeVersion")
    implementation(npm("electron", "^$electronVersion"))
    implementation(npm("@electron/remote", "^$electronRemoteVersion"))
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
