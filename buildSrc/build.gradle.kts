plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    implementation("de.marcphilipp.gradle:nexus-publish-plugin:0.4.0")
    implementation(gradleApi())
}
