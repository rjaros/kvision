plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val popperjsCoreVersion: String by project
val bootstrapVersion: String by project

kotlin {
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(rootProject)
                implementation(npm("@popperjs/core", "^$popperjsCoreVersion"))
                implementation(npm("bootstrap", "^$bootstrapVersion"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(project(":kvision-modules:kvision-testutils"))
                implementation(project(":kvision-modules:kvision-jquery"))
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
