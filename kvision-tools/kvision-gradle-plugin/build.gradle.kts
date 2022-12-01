plugins {
    `kotlin-dsl`
    kotlin("jvm")
    id("java-gradle-plugin")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
    val gradlePluginPublishVersion: String by System.getProperties()
    id("com.gradle.plugin-publish") version gradlePluginPublishVersion
}

val kotlinVersion: String by System.getProperties()
val autoServiceVersion: String by project

gradlePlugin {
    plugins {
        create("kvisionGradlePlugin") {
            displayName = kvisionProjectName
            description = kvisionProjectDescription
            id = "io.kvision"
            implementationClass = "io.kvision.gradle.KVisionPlugin"
        }
    }
}

pluginBundle {
    website = kvisionProjectWebsite
    vcsUrl = kvisionVcsUrl
    tags = listOf("kvision", "kotlin", "kotlin-js", "kotlin-multiplatform")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val kotestVersion: String by project
val kspVersion: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin"))
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:$kspVersion")

    testImplementation(gradleTestKit())

    testImplementation(platform("io.kotest:kotest-bom:$kotestVersion"))
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")
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
afterEvaluate {
    publishing {
        publications {
            getByName<MavenPublication>("pluginMaven") {
                artifact(tasks["sourcesJar"])
                if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
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
