plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
}

val kotlinVersion: String by System.getProperties()
val autoServiceVersion: String by project

gradlePlugin {
    plugins {
        isAutomatedPublishing = false
        create("simplePlugin") {
            id = "io.kvision"
            implementationClass = "io.kvision.gradle.KVisionGradleSubplugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:$kotlinVersion")
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

setupSigning()
setupPublication()
