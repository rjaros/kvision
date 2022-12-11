import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
}

val javaVersion: String by project
val kspVersion: String by project

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = javaVersion
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions.jvmTarget = javaVersion

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":kvision-modules:kvision-common-annotations"))
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
}

val sourcesJar by tasks.registering(Jar::class) {
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
