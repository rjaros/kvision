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
    js(IR) {
        compilations.all {
            kotlinOptions {
                moduleKind = "umd"
            }
        }
        nodejs {
            testTask(Action {
                useKarma()
            })
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
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka/html"))

}

setupSigning()
setupPublication()
setupDokkaMpp()
