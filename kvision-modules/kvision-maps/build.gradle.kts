plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
}

val kotestVersion: String by project

kotlin {
    kotlinJsTargets()
}

dependencies {
    api(rootProject)
    implementation(kotlin("stdlib-js"))

    implementation(npm("leaflet", "^1.8.0"))
//    implementation(npm("@types/leaflet", "^1.8.0"))

    implementation(npm("geojson", "^0.5.0")) {
        because("used by Leaflet for defining locations")
    }
    implementation(npm("@types/geojson", "7946.0.8"))

    testImplementation(kotlin("test-js"))
    testImplementation(project(":kvision-modules:kvision-testutils"))
    testImplementation(platform("io.kotest:kotest-bom:$kotestVersion"))
    testImplementation("io.kotest:kotest-assertions-core")
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
        }
    }
}

setupSigning()
setupPublication()
setupDokka()
