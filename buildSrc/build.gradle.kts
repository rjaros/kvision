plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.20")
    implementation("io.github.gradle-nexus:publish-plugin:2.0.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:2.0.0")
    implementation(gradleApi())
}
