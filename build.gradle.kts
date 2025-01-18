import java.net.URI

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
val materialVersion: String by project
val onsenuiVersion: String by project
val paceProgressbarVersion: String by project
val printjsVersion: String by project
val reactVersion: String by project
val trixVersion: String by project
val tabulatorTablesVersion: String by project
val toastifyjsVersion: String by project
val tomSelectVersion: String by project

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
        resolution("@material/web", materialVersion)
        resolution("onsenui", onsenuiVersion)
        resolution("pace-progressbar", paceProgressbarVersion)
        resolution("print-js", printjsVersion)
        resolution("react", reactVersion)
        resolution("react-dom", reactVersion)
        resolution("trix", trixVersion)
        resolution("tabulator-tables", tabulatorTablesVersion)
        resolution("toastify-js", toastifyjsVersion)
        resolution("tom-select", tomSelectVersion)
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
    dependsOn("dokkaGenerate")
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka/html"))

}

setupSigning()
setupPublication(withSigning = true)

dependencies {
    dokka(rootProject)
    dokka(project(":kvision-modules:kvision-ballast"))
    dokka(project(":kvision-modules:kvision-bootstrap"))
    dokka(project(":kvision-modules:kvision-bootstrap-icons"))
    dokka(project(":kvision-modules:kvision-bootstrap-upload"))
    dokka(project(":kvision-modules:kvision-chart"))
    dokka(project(":kvision-modules:kvision-common-annotations"))
    dokka(project(":kvision-modules:kvision-common-remote"))
    dokka(project(":kvision-modules:kvision-common-types"))
    dokka(project(":kvision-modules:kvision-cordova"))
    dokka(project(":kvision-modules:kvision-datetime"))
    dokka(project(":kvision-modules:kvision-electron"))
    dokka(project(":kvision-modules:kvision-fontawesome"))
    dokka(project(":kvision-modules:kvision-handlebars"))
    dokka(project(":kvision-modules:kvision-i18n"))
    dokka(project(":kvision-modules:kvision-imask"))
    dokka(project(":kvision-modules:kvision-jquery"))
    dokka(project(":kvision-modules:kvision-maps"))
    dokka(project(":kvision-modules:kvision-material"))
    dokka(project(":kvision-modules:kvision-onsenui"))
    dokka(project(":kvision-modules:kvision-pace"))
    dokka(project(":kvision-modules:kvision-print"))
    dokka(project(":kvision-modules:kvision-react"))
    dokka(project(":kvision-modules:kvision-redux-kotlin"))
    dokka(project(":kvision-modules:kvision-rest"))
    dokka(project(":kvision-modules:kvision-richtext"))
    dokka(project(":kvision-modules:kvision-routing-ballast"))
    dokka(project(":kvision-modules:kvision-routing-navigo"))
    dokka(project(":kvision-modules:kvision-routing-navigo-ng"))
    dokka(project(":kvision-modules:kvision-select-remote"))
    dokka(project(":kvision-modules:kvision-server-javalin"))
    dokka(project(":kvision-modules:kvision-server-jooby"))
    dokka(project(":kvision-modules:kvision-server-ktor"))
    dokka(project(":kvision-modules:kvision-server-ktor-koin"))
    dokka(project(":kvision-modules:kvision-server-micronaut"))
    dokka(project(":kvision-modules:kvision-server-spring-boot"))
    dokka(project(":kvision-modules:kvision-server-vertx"))
    dokka(project(":kvision-modules:kvision-state"))
    dokka(project(":kvision-modules:kvision-state-flow"))
    dokka(project(":kvision-modules:kvision-tabulator"))
    dokka(project(":kvision-modules:kvision-tabulator-remote"))
    dokka(project(":kvision-modules:kvision-testutils"))
    dokka(project(":kvision-modules:kvision-toastify"))
    dokka(project(":kvision-modules:kvision-tom-select"))
    dokka(project(":kvision-modules:kvision-tom-select-remote"))
}

dokka {
    modulePath = "kvision"
    dokkaSourceSets {
        configureEach {
            includes.from("Module.md")
            sourceLink {
                localDirectory.set(projectDir.resolve("src"))
                remoteUrl.set(URI("https://github.com/rjaros/kvision/tree/master/src"))
                remoteLineSuffix.set("#L")
            }
        }
    }
    dokkaGeneratorIsolation = ProcessIsolation {
        maxHeapSize = "6g"
    }
}
