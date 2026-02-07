plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlinx.serialization)
    id("java-gradle-plugin")
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
    alias(libs.plugins.gradle.plugin.publish)
}

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
    kotlinJvmTargets()
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation(libs.tomlj)

    testImplementation(gradleTestKit())

    testImplementation(platform("io.kotest:kotest-bom:${libs.versions.kotest.get()}"))
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")
}

tasks {
    val versionsFile = rootProject.layout.projectDirectory.file("gradle/libs.versions.toml")
    val version = project.version
    getByName("jar", Jar::class) {
        from(versionsFile) {
            rename { "io.kvision.versions.toml" }
            filter { line -> line.replaceAfter("kvision = ", "\"${version}\"") }
        }
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                defaultPom()
            }
        }
    }
}

tasks.getByName("dokkaGeneratePublicationHtml").run {
    enabled = !project.hasProperty("SNAPSHOT")
}

extensions.getByType<SigningExtension>().run {
    isRequired = !project.hasProperty("SNAPSHOT")
    sign(extensions.getByType<PublishingExtension>().publications)
}

setupDokka(tasks.dokkaGeneratePublicationHtml, modulesPath = "kvision-tools/")

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
