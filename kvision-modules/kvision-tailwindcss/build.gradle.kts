plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val postcssVersion: String by project
val postcssLoaderVersion: String by project
val tailwindcssVersion: String by project
val cssnanoVersion: String by project

kotlin {
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(rootProject)
                implementation(npm("postcss", postcssVersion))
                implementation(npm("postcss-loader", postcssLoaderVersion))
                implementation(npm("tailwindcss", tailwindcssVersion))
                implementation(npm("@tailwindcss/postcss", tailwindcssVersion))
                implementation(npm("cssnano", cssnanoVersion))
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
    dependsOn("dokkaGenerate")
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka/html"))

}

setupSigning()
setupPublication()
setupDokka()
