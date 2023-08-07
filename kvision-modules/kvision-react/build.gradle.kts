plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val kotlinReactVersion: String by project
val reactVersion: String by project

kotlin {
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(rootProject)
                api("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlinReactVersion")
                api("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlinReactVersion")
                implementation(npm("react", "^$reactVersion"))
                implementation(npm("react-dom", "^$reactVersion"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(project(":kvision-modules:kvision-testutils"))
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
