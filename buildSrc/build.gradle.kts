plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
    implementation("de.marcphilipp.gradle:nexus-publish-plugin:0.4.0")
    implementation(gradleApi())
}
