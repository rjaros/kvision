plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
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

// Custom Properties
val webDir = file("src/main/web")

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(project(":kvision-modules:kvision-common-types"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-json-js:$serializationVersion")
//    for local development
//    implementation(npm("kvision-assets", "http://localhost:8001/kvision-assets-5.0.0.tgz"))
    implementation(npm("kvision-assets", "^5.0.7"))
    implementation(npm("css-loader", "^6.5.1"))
    implementation(npm("style-loader", "^3.3.1"))
    implementation(npm("imports-loader", "^3.1.1"))
    implementation(npm("fecha", "^4.2.1"))
    implementation(npm("snabbdom", "^3.1.0"))
    implementation(npm("@rjaros/snabbdom-virtualize", "^1.0.0-beta.0"))
    implementation(npm("split.js", "^1.6.4"))
    implementation(npm("gettext.js", "^1.0.1"))
    implementation(npm("gettext-extract", "^2.0.1"))
    testImplementation(kotlin("test-js"))
    testImplementation(project(":kvision-modules:kvision-testutils"))
}

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn("irGenerateExternalsIntegrated")
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
            pom {
                defaultPom()
            }
        }
    }
}

setupSigning()
setupPublication()

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
            sourceRoots.from(file("kvision-modules/kvision-bootstrap/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-css/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-datetime/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-dialog/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-icons/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-select/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-select-remote/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-spinner/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-typeahead/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-typeahead-remote/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-bootstrap-upload/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-chart/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-common-remote/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-common-types/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-cordova/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-datacontainer/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-electron/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-fontawesome/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-handlebars/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-i18n/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-jquery/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-maps/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-moment/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-onsenui/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-pace/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-react/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-redux/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-rest/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-richtext/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-routing-navigo-ng/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-server-ktor/src/jsMain/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-simple-select-remote/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-state/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-state-flow/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tabulator/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-tabulator-remote/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-testutils/src/main/kotlin"))
            sourceRoots.from(file("kvision-modules/kvision-toast/src/main/kotlin"))
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
