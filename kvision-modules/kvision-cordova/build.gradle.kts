plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

kotlin {
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(rootProject)
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
