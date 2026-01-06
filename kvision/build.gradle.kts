plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

kotlin {
    compilerOptions()
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(project(":kvision-modules:kvision-common-types"))
                api(libs.kotlinx.coroutines.core.js)
                api(libs.kotlinx.serialization.json.js)
//    for local development
//    implementation(npm("zzz-kvision-assets", "http://localhost:8001/kvision-assets-8.0.6.tgz"))
                implementation(npm("zzz-kvision-assets", libs.versions.npm.kvision.assets.get()))
                implementation(npm("css-loader", libs.versions.css.loader.get()))
                implementation(npm("style-loader", libs.versions.style.loader.get()))
                implementation(npm("fecha", libs.versions.fecha.get()))
                implementation(npm("snabbdom", libs.versions.snabbdom.asProvider().get()))
                implementation(npm("@rjaros/snabbdom-virtualize", libs.versions.snabbdom.virtualize.get()))
                implementation(npm("split.js", libs.versions.splitjs.get()))
                implementation(npm("gettext.js", libs.versions.gettext.js.get()))
                implementation(npm("gettext-extract", libs.versions.gettext.extract.get()))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(devNpm("karma-junit-reporter", libs.versions.karma.junit.reporter.get()))
                implementation(kotlin("test-js"))
                implementation(project(":kvision-modules:kvision-testutils"))
            }
        }
    }
}

setupDokka(tasks.dokkaGeneratePublicationHtml, mdPath = "../", modulesPath = "")
setupPublication()
