buildscript {
    extra.set("production", (findProperty("prod") ?: findProperty("production") ?: "false") == "true")
}

plugins {
    val kotlinVersion: String by System.getProperties()
    id("kotlinx-serialization") version kotlinVersion
    kotlin("js")
    id("maven-publish")
    val dokkaVersion: String by System.getProperties()
    id("org.jetbrains.dokka") version dokkaVersion
}

repositories()

// Versions
val kotlinVersion: String by System.getProperties()
val serializationVersion: String by project
val coroutinesVersion: String by project
val snabbdomKotlinVersion: String by project
val navigoKotlinVersion: String by project
val jqueryKotlinVersion: String by project

// Custom Properties
val webDir = file("src/main/web")

kotlin {
    kotlinJsTargets()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    api(project(":kvision-modules:kvision-common-types"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serializationVersion")
    api("com.github.snabbdom:snabbdom-kotlin:$snabbdomKotlinVersion")
    api("pl.treksoft:navigo-kotlin:$navigoKotlinVersion")
    api("pl.treksoft:jquery-kotlin:$jqueryKotlinVersion")
    implementation(npm("css-loader", "^3.5.2"))
    implementation(npm("style-loader", "^1.1.4"))
    implementation(npm("less", "^3.11.1"))
    implementation(npm("less-loader", "^5.0.0"))
    implementation(npm("imports-loader", "^0.8.0"))
    implementation(npm("uglifyjs-webpack-plugin", "^2.2.0"))
    implementation(npm("file-loader", "^6.0.0"))
    implementation(npm("url-loader", "^4.1.0"))
    implementation(npm("jquery", "^3.4.1"))
    implementation(npm("fecha", "^4.2.0"))
    implementation(npm("snabbdom", "^0.7.4"))
    implementation(npm("snabbdom-virtualize", "^0.7.0"))
    implementation(npm("jquery-resizable-dom", "^0.35.0"))
    implementation(npm("navigo", "^7.1.2"))
    testImplementation(kotlin("test-js"))
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.main.get().kotlin)
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            pom {
                defaultPom()
            }
        }
    }
}

setupPublication()

fun copyResources() {
    copy {
        from("$buildDir/processedResources/Js/main")
        into("${rootProject.buildDir}/js/packages/kvision/kotlin")
    }
    copy {
        from("$buildDir/processedResources/Js/main")
        into("${rootProject.buildDir}/js/packages/kvision/kotlin-dce")
    }
}

tasks {
    /*
    // Forcing specific npm package versions (not required at the moment but commented for future use)
    withType<org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask> {
        doLast {
            yarnLock.parentFile.resolve("package.json").apply {
                writeText(readText().replace(
                    "\"dependencies\": {},",
                    "\"dependencies\": {},\n  \"resolutions\": { \"jquery\": \"3.4.1\" },"
                ))
            }
            org.jetbrains.kotlin.gradle.targets.js.yarn.YarnWorkspaces()
                .yarnExec(project, yarnLock.parentFile, "Relaunching Yarn to fix resolutions")
        }
    }
    */
    getByName("JsJar", Jar::class) {
        from("${project.buildDir}/js/packages/kvision/package.json")
    }
    getByName("compileTestKotlinJs") {
        doLast {
            copyResources()
        }
    }
    getByName("processDceKotlinJs") {
        doLast {
            copyResources()
        }
    }
    val dokka by getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/kdoc"
        subProjects = listOf(
            "kvision-bootstrap",
            "kvision-bootstrap-css",
            "kvision-bootstrap-datetime",
            "kvision-bootstrap-dialog",
            "kvision-bootstrap-select",
            "kvision-bootstrap-select-remote",
            "kvision-bootstrap-spinner",
            "kvision-bootstrap-typeahead",
            "kvision-bootstrap-typeahead-remote",
            "kvision-bootstrap-upload",
            "kvision-common-annotations",
            "kvision-common-remote",
            "kvision-common-types",
            "kvision-chart",
            "kvision-cordova",
            "kvision-datacontainer",
            "kvision-electron",
            "kvision-event-flow",
            "kvision-fontawesome",
            "kvision-handlebars",
            "kvision-i18n",
            "kvision-maps",
            "kvision-moment",
            "kvision-pace",
            "kvision-react",
            "kvision-redux",
            "kvision-redux-kotlin",
            "kvision-richtext",
            "kvision-tabulator",
            "kvision-tabulator-remote",
            "kvision-server-javalin",
            "kvision-server-vertx",
            "kvision-toast"
        )
        multiplatform {
            val js by creating {
                includes = listOf("Module.md")
                reportUndocumented = false
            }
        }
    }
}
