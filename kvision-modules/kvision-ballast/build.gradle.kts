plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val ballastVersion: String by project

kotlin {
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(rootProject)
                api(project(":kvision-modules:kvision-state-flow"))
                api("io.github.copper-leaf:ballast-core:$ballastVersion")
                api("io.github.copper-leaf:ballast-saved-state:$ballastVersion")
                api("io.github.copper-leaf:ballast-repository:$ballastVersion")
                api("io.github.copper-leaf:ballast-sync:$ballastVersion")
                api("io.github.copper-leaf:ballast-undo:$ballastVersion")
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
