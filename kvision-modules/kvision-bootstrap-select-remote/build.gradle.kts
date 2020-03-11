buildscript {
    extra.set("production", (findProperty("prod") ?: findProperty("production") ?: "false") == "true")
}

plugins {
    kotlin("js")
    id("maven-publish")
}

repositories()

kotlin {
    kotlinJsTargets()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    api(rootProject)
    api(project(":kvision-modules:kvision-bootstrap-select"))
    api(project(":kvision-modules:kvision-common-remote"))
    compileOnly(project(":kvision-modules:kvision-server-javalin"))
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
        into("${rootProject.buildDir}/js/packages/kvision-${project.name}/kotlin")
    }
    copy {
        from("$buildDir/processedResources/Js/main")
        into("${rootProject.buildDir}/js/packages/kvision-${project.name}/kotlin-dce")
    }
    copy {
        from("${rootProject.projectDir}/kvision-modules/kvision-bootstrap-select/src/main/resources")
        into("${rootProject.buildDir}/js/packages/kvision-${project.name}/kotlin")
    }
    copy {
        from("${rootProject.projectDir}/kvision-modules/kvision-bootstrap-select/src/main/resources")
        into("${rootProject.buildDir}/js/packages/kvision-${project.name}/kotlin-dce")
    }
}

tasks {
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
}
