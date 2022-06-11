plugins {
    `kotlin-dsl`
    kotlin("jvm")
    id("java-gradle-plugin")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")

    id("com.gradle.plugin-publish") version "0.21.0"
}

val kotlinVersion: String by System.getProperties()
val autoServiceVersion: String by project

gradlePlugin {
    plugins {
        create("kvisionGradlePlugin") {
            id = "io.kvision"
            implementationClass = "io.kvision.gradle.KVisionGradleSubplugin"
//            implementationClass = "io.kvision.gradle.KVisionPlugin"
        }
    }
}

pluginBundle {
    website = kvisionProjectWebsite
    vcsUrl = kvisionVcsUrl
    tags = listOf("kvision", "kotlin", "kotlin-js", "kotlin-multiplatform")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val kotestVersion: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin"))

    testImplementation(gradleTestKit())

    implementation(platform("io.kotest:kotest-bom:$kotestVersion"))
    implementation("io.kotest:kotest-runner-junit5")
    implementation("io.kotest:kotest-assertions-core")
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
            pom {
                defaultPom()
            }
        }
    }
}

setupSigning()
setupPublication()
setupDokka()

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
