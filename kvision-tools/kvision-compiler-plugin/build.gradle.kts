plugins {
    kotlin("jvm")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
}

val mpaptRuntimeVersion: String by project

repositories()

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("de.jensklingenberg:mpapt-runtime:$mpaptRuntimeVersion")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.main.get().kotlin)
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            pom {
                defaultPom()
            }
        }
    }
}

setupSigning()
setupPublication()
