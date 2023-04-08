plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
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
