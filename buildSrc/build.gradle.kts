plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    mavenLocal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
    implementation(gradleApi())
}
