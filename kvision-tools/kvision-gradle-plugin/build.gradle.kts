plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("maven-publish")
}

val kotlinVersion: String by System.getProperties()
val autoServiceVersion: String by project

repositories()

gradlePlugin {
    plugins {
        isAutomatedPublishing = false
        create("simplePlugin") {
            id = "pl.treksoft.kvision"
            implementationClass = "pl.treksoft.kvision.gradle.KVisionGradlePlugin"
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

setupPublication()
