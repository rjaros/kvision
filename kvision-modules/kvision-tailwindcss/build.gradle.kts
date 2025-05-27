plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.nmcp)
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

kotlin {
    compilerOptions()
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(project(":kvision"))
                implementation(npm("postcss", libs.versions.postcss.asProvider().get()))
                implementation(npm("postcss-loader", libs.versions.postcss.loader.get()))
                implementation(npm("tailwindcss", libs.versions.tailwindcss.get()))
                implementation(npm("@tailwindcss/postcss", libs.versions.tailwindcss.get()))
                implementation(npm("cssnano", libs.versions.cssnano.get()))
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

setupDokka(tasks.dokkaGeneratePublicationHtml)
setupPublication()
