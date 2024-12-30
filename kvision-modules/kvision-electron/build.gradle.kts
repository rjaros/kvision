plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val kotlinNodeVersion: String by project
val electronVersion: String by project
val electronRemoteVersion: String by project

kotlin {
    compilerOptions()
    js(IR) {
        nodejs {
            testTask {
                useKarma()
            }
        }
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(rootProject)
                api("org.jetbrains.kotlin-wrappers:kotlin-node:$kotlinNodeVersion")
                implementation(npm("electron", electronVersion))
                implementation(npm("@electron/remote", electronRemoteVersion))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaGenerate")
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka/html"))

}

setupSigning()
setupPublication()
setupDokka()
