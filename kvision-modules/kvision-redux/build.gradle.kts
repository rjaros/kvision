plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val kotlinReduxVersion: String by project
val reduxVersion: String by project
val reduxThunkVersion: String by project

kotlin {
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(rootProject)
                api("org.jetbrains.kotlin-wrappers:kotlin-redux:$kotlinReduxVersion") {
                    exclude("org.jetbrains.kotlinx", "kotlinx-html-js")
                }
                implementation(npm("redux", "^$reduxVersion"))
                implementation(npm("redux-thunk", "^$reduxThunkVersion"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(project(":kvision-modules:kvision-testutils"))
                implementation(project(":kvision-modules:kvision-state"))
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
