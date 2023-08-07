plugins {
    `kotlin-dsl`
    kotlin("jvm")
    id("java-gradle-plugin")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
    val gradlePluginPublishVersion: String by System.getProperties()
    id("com.gradle.plugin-publish") version gradlePluginPublishVersion
}

val javaVersion: String by project
val kotlinVersion: String by System.getProperties()
val autoServiceVersion: String by project
val kotestVersion: String by project
val kspVersion: String by project
val springBootVersion: String by project
val shadowVersion: String by project

repositories {
    gradlePluginPortal()
}

gradlePlugin {
    website.set(kvisionProjectWebsite)
    vcsUrl.set(kvisionVcsUrl)
    plugins {
        create("kvisionGradlePlugin") {
            displayName = kvisionProjectName
            description = kvisionProjectDescription
            id = "io.kvision"
            implementationClass = "io.kvision.gradle.KVisionPlugin"
            tags.set(listOf("kvision", "kotlin", "kotlin-js", "kotlin-multiplatform"))
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:$kspVersion")
    compileOnly("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    compileOnly("com.github.johnrengelman.shadow:com.github.johnrengelman.shadow.gradle.plugin:$shadowVersion")

    testImplementation(gradleTestKit())

    testImplementation(platform("io.kotest:kotest-bom:$kotestVersion"))
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka/html"))

    enabled = !hasProperty("SNAPSHOT")
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                defaultPom()
            }
        }
    }
}

setupSigning()
setupDokka()

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
