plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0-RC2")
    implementation("de.marcphilipp.gradle:nexus-publish-plugin:0.4.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.7.20") {
        exclude("org.jetbrains.kotlin","kotlin-stdlib-jdk8")
    }
    implementation(gradleApi())
}
