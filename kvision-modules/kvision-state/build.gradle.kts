plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
}

val diffVersion: String by project

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    implementation("io.github.petertrr:kotlin-multiplatform-diff-js:$diffVersion")
    testImplementation(kotlin("test-js"))
    testImplementation(project(":kvision-modules:kvision-testutils"))
    testImplementation(project(":kvision-modules:kvision-jquery"))
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
setupPublication()
setupDokka()
