plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val leafletVersion: String by project
val geojsonVersion: String by project
val geojsonTypesVersion: String by project
val kotestVersion: String by project

kotlin {
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(rootProject)
                implementation(kotlin("stdlib-js"))
                implementation(npm("leaflet", leafletVersion))
                implementation(npm("geojson", geojsonVersion)) {
                    because("used by Leaflet for defining locations")
                }
                implementation(npm("@types/geojson", geojsonTypesVersion))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(project(":kvision-modules:kvision-testutils"))
                implementation(project.dependencies.platform("io.kotest:kotest-bom:$kotestVersion"))
                implementation("io.kotest:kotest-assertions-core")
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
setupPublication()
setupDokka()
