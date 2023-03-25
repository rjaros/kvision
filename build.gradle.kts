plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("js")
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
val webDir = file("src/main/web")

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        lockFileDirectory = project.rootDir.resolve(".kotlin-js-store")
    }
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().apply {
        versions.webpackDevServer.version = "4.13.1"
        versions.webpack.version = "5.76.3"
        versions.webpackCli.version = "5.0.1"
        versions.karma.version = "6.4.1"
        versions.mocha.version = "10.2.0"
    }
}

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(project(":kvision-modules:kvision-common-types"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json-js:$serializationVersion")
//    for local development
//    implementation(npm("kvision-assets", "http://localhost:8001/kvision-assets-8.0.0.tgz"))
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
    implementation(devNpm("karma-junit-reporter", "2.0.1"))
    testImplementation(kotlin("test-js"))
    testImplementation(project(":kvision-modules:kvision-testutils"))
}

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn("generateExternalsIntegrated")
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.main.get().kotlin)
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from("$buildDir/dokka/html")
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
        }
    }
}

setupSigning()
setupRootPublication()

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        named("main") {
            suppress.set(true)
        }
        register("kvision") {
            includes.from("Module.md")
            displayName.set("js")
            platform.set(org.jetbrains.dokka.Platform.js)
            includeNonPublic.set(false)
            skipDeprecated.set(false)
            reportUndocumented.set(false)
            sourceRoots.from(file("src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-ballast/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-icons/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-upload/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-chart/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-common-remote/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-common-types/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-cordova/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-datetime/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-electron/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-fontawesome/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-handlebars/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-i18n/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-imask/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-jquery/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-maps/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-onsenui/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-pace/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-react/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-redux/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-rest/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-richtext/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-routing-navigo-ng/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-server-ktor/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-select-remote/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-state/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-state-flow/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tabulator/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tabulator-remote/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-testutils/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-toastify/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tom-select/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tom-select-remote/src/main/kotlin"))
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
