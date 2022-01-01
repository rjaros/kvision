import org.jetbrains.kotlin.util.prefixIfNot

plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
}

val coroutinesVersion: String by project


kotlin {
    kotlinJsTargets()
}

val kotlinWrappersVersion = "0.0.1-pre.284-kotlin-1.6.10"
fun kotlinJsW( // experimenting, probably remove this
    target: String,
    version: String? = null
): String = "org.jetbrains.kotlin-wrappers:kotlin-$target" + (version?.prefixIfNot(":") ?: "")

dependencies {
    api(rootProject)
    implementation(kotlin("stdlib-js"))

    implementation(npm("leaflet", "^1.7.1", false))
    implementation(npm("@types/leaflet", "^1.7.7", false)) {
        because("Kotlin/JS interop... I think?")
    }

    implementation(npm("geojson", "^0.5.0", false)) {
        because("used by Leaflet for defining locations")
    }
    implementation(npm("@types/geojson", "7946.0.8", false)) // unsure if this is necessary

    testImplementation(kotlin("test-js"))
    testImplementation(project(":kvision-modules:kvision-testutils"))
    testImplementation(project(":kvision-modules:kvision-jquery"))  // experimenting - remove later

    // experimenting - remove later
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    val kotestVersion: String by project
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
            pom {
                defaultPom()
            }
        }
    }
}

setupSigning()
setupPublication()
setupDokka()
