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
val karmaJunitReporterVersion: String by project

val popperjsCoreVersion: String by project
val bootstrapVersion: String by project
val bootstrapIconsVersion: String by project
val bootstrapFileinputVersion: String by project
val chartjsVersion: String by project
val tempusDominusVersion: String by project
val electronVersion: String by project
val electronRemoteVersion: String by project
val fontawesomeFreeVersion: String by project
val handlebarsVersion: String by project
val handlebarsLoaderVersion: String by project
val imaskVersion: String by project
val jqueryVersion: String by project
val leafletVersion: String by project
val geojsonVersion: String by project
val geojsonTypesVersion: String by project
val onsenuiVersion: String by project
val paceProgressbarVersion: String by project
val printjsVersion: String by project
val reactVersion: String by project
val reduxVersion: String by project
val reduxThunkVersion: String by project
val trixVersion: String by project
val tabulatorTablesVersion: String by project
val toastifyjsVersion: String by project
val tomSelectVersion: String by project

// Custom Properties
val webDir = file("src/jsMain/web")

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        lockFileDirectory = project.rootDir.resolve(".kotlin-js-store")
        resolution("kvision-assets", kvisionAssetsVersion)
        resolution("css-loader", cssLoaderVersion)
        resolution("style-loader", styleLoaderVersion)
        resolution("imports-loader", importsLoaderVersion)
        resolution("fecha", fechaVersion)
        resolution("snabbdom", snabbdomVersion)
        resolution("@rjaros/snabbdom-virtualize", snabbdomVirtualizeVersion)
        resolution("split.js", splitjsVersion)
        resolution("gettext.js", gettextjsVersion)
        resolution("gettext-extract", gettextExtractVersion)
        resolution("karma-junit-reporter", karmaJunitReporterVersion)
        resolution("@popperjs/core", popperjsCoreVersion)
        resolution("bootstrap", bootstrapVersion)
        resolution("bootstrap-icons", bootstrapIconsVersion)
        resolution("bootstrap-fileinput", bootstrapFileinputVersion)
        resolution("chart.js", chartjsVersion)
        resolution("@eonasdan/tempus-dominus", tempusDominusVersion)
        resolution("electron", electronVersion)
        resolution("@electron/remote", electronRemoteVersion)
        resolution("@fortawesome/fontawesome-free", fontawesomeFreeVersion)
        resolution("handlebars", handlebarsVersion)
        resolution("handlebars-loader", handlebarsLoaderVersion)
        resolution("imask", imaskVersion)
        resolution("jquery", jqueryVersion)
        resolution("leaflet", leafletVersion)
        resolution("geojson", geojsonVersion)
        resolution("@types/geojson", geojsonTypesVersion)
        resolution("onsenui", onsenuiVersion)
        resolution("pace-progressbar", paceProgressbarVersion)
        resolution("print-js", printjsVersion)
        resolution("react", reactVersion)
        resolution("react-dom", reactVersion)
        resolution("redux", reduxVersion)
        resolution("redux-thunk", reduxThunkVersion)
        resolution("trix", trixVersion)
        resolution("tabulator-tables", tabulatorTablesVersion)
        resolution("toastify-js", toastifyjsVersion)
        resolution("@remotedevforce/tom-select", tomSelectVersion)
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
                implementation(npm("kvision-assets", kvisionAssetsVersion))
                implementation(npm("css-loader", cssLoaderVersion))
                implementation(npm("style-loader", styleLoaderVersion))
                implementation(npm("imports-loader", importsLoaderVersion))
                implementation(npm("fecha", fechaVersion))
                implementation(npm("snabbdom", snabbdomVersion))
                implementation(npm("@rjaros/snabbdom-virtualize", snabbdomVirtualizeVersion))
                implementation(npm("split.js", splitjsVersion))
                implementation(npm("gettext.js", gettextjsVersion))
                implementation(npm("gettext-extract", gettextExtractVersion))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(devNpm("karma-junit-reporter", karmaJunitReporterVersion))
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
