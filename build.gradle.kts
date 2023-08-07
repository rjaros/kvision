plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("io.github.gradle-nexus.publish-plugin")
    id("org.jetbrains.dokka")
}

allprojects {
    repositories()
    version = project.properties["versionNumber"]!!
    if (hasProperty("SNAPSHOT")) {
        version = "$version-SNAPSHOT"
    }
}

// Versions
val kotlinVersion: String by System.getProperties()
val serializationVersion: String by project
val coroutinesVersion: String by project
val snabbdomKotlinVersion: String by project

val kvisionAssetsVersion: String by project
val cssLoaderVersion: String by project
val styleLoaderVersion: String by project
val importsLoaderVersion: String by project
val fechaVersion: String by project
val snabbdomVersion: String by project
val snabbdomVirtualizeVersion: String by project
val splitjsVersion: String by project
val gettextjsVersion: String by project
val gettextExtractVersion: String by project

// Custom Properties
val webDir = file("src/jsMain/web")

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        lockFileDirectory = project.rootDir.resolve(".kotlin-js-store")
    }
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().apply {
        versions.webpackDevServer.version = "4.15.1"
        versions.webpack.version = "5.88.2"
        versions.webpackCli.version = "5.1.4"
        versions.karma.version = "6.4.2"
        versions.mocha.version = "10.2.0"
    }
}

kotlin {
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(project(":kvision-modules:kvision-common-types"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json-js:$serializationVersion")
//    for local development
//    implementation(npm("kvision-assets", "http://localhost:8001/kvision-assets-8.0.6.tgz"))
                implementation(npm("kvision-assets", "^$kvisionAssetsVersion"))
                implementation(npm("css-loader", "^$cssLoaderVersion"))
                implementation(npm("style-loader", "^$styleLoaderVersion"))
                implementation(npm("imports-loader", "^$importsLoaderVersion"))
                implementation(npm("fecha", "^$fechaVersion"))
                implementation(npm("snabbdom", "^$snabbdomVersion"))
                implementation(npm("@rjaros/snabbdom-virtualize", "^$snabbdomVirtualizeVersion"))
                implementation(npm("split.js", "^$splitjsVersion"))
                implementation(npm("gettext.js", "^$gettextjsVersion"))
                implementation(npm("gettext-extract", "^$gettextExtractVersion"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(devNpm("karma-junit-reporter", "2.0.1"))
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
setupPublication(withSigning = true)

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        named("commonMain") {
            suppress.set(true)
        }
        named("jsMain") {
            suppress.set(true)
        }
        register("kvision") {
            includes.from("Module.md")
            displayName.set("js")
            platform.set(org.jetbrains.dokka.Platform.js)
            includeNonPublic.set(false)
            skipDeprecated.set(false)
            reportUndocumented.set(false)
            sourceRoots.from(file("src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-ballast/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-icons/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-upload/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-chart/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-common-remote/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-common-types/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-cordova/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-datetime/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-electron/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-fontawesome/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-handlebars/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-i18n/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-imask/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-jquery/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-maps/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-onsenui/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-pace/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-react/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-redux/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-rest/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-richtext/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-routing-navigo-ng/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-server-ktor/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-select-remote/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-state/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-state-flow/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tabulator/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tabulator-remote/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-testutils/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-toastify/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tom-select/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tom-select-remote/src/jsMain/kotlin"))
        }
        register("kvision-common") {
            includes.from("Module.md")
            displayName.set("common")
            platform.set(org.jetbrains.dokka.Platform.common)
            includeNonPublic.set(false)
            skipDeprecated.set(false)
            reportUndocumented.set(false)
            sourceRoots.from(file("kvision-modules/kvision-common-annotations/src/commonMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-common-remote/src/commonMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-common-types/src/commonMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-server-ktor/src/commonMain/kotlin"))
        }
        register("kvision-jvm") {
            includes.from("Module.md")
            displayName.set("jvm")
            platform.set(org.jetbrains.dokka.Platform.jvm)
            includeNonPublic.set(false)
            skipDeprecated.set(false)
            reportUndocumented.set(false)
            sourceRoots.from(file("kvision-modules/kvision-common-remote/src/jvmMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-common-types/src/jvmMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-server-ktor/src/jvmMain/kotlin"))
        }
    }
}
