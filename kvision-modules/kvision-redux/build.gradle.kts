plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val kotlinReduxVersion: String by project
val reduxVersion: String by project
val reduxThunkVersion: String by project

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    api("org.jetbrains.kotlin-wrappers:kotlin-redux:$kotlinReduxVersion") {
        exclude("org.jetbrains.kotlinx", "kotlinx-html-js")
    }
    implementation(npm("redux", "^$reduxVersion"))
    implementation(npm("redux-thunk", "^$reduxThunkVersion"))
    testImplementation(kotlin("test-js"))
    testImplementation(project(":kvision-modules:kvision-testutils"))
    testImplementation(project(":kvision-modules:kvision-state"))
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
            if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
        }
    }
}

setupSigning()
setupPublication()
setupDokka()
