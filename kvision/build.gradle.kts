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
        getByName("jsMain") {
            dependencies {
                api(project(":kvision-modules:kvision-common-types"))
                api(libs.kotlinx.coroutines.core.js)
                api(libs.kotlinx.serialization.json.js)
//    for local development
//    npm("zzz-kvision-assets", "http://localhost:8001/kvision-assets-8.0.6.tgz")
                npm("zzz-kvision-assets", libs.versions.npm.kvision.assets.get())
                npm("css-loader", libs.versions.css.loader.get())
                npm("style-loader", libs.versions.style.loader.get())
                npm("fecha", libs.versions.fecha.get())
                npm("snabbdom", libs.versions.snabbdom.asProvider().get())
                npm("@appcues/snabbdom-virtualize", libs.versions.snabbdom.virtualize.get())
                npm("split.js", libs.versions.splitjs.get())
                npm("gettext.js", libs.versions.gettext.js.get())
                npm("gettext-extract", libs.versions.gettext.extract.get())
            }
        }
        getByName("jsTest") {
            dependencies {
                npmDev("karma-junit-reporter", libs.versions.karma.junit.reporter.get())
                implementation(kotlin("test-js"))
                implementation(project(":kvision-modules:kvision-testutils"))
            }
        }
    }
}

setupDokka(mdPath = "../", modulesPath = "")
setupPublication()
