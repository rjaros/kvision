plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
}

val jqueryKotlinVersion: String by project
val kotestVersion: String by project

kotlin {
    kotlinJsTargets()

    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }
}

dependencies {
    api("io.kvision:jquery-kotlin:$jqueryKotlinVersion")
    api(rootProject)
    api(kotlin("test-js"))

    implementation(platform("io.kotest:kotest-bom:$kotestVersion"))
    implementation("io.kotest:kotest-assertions-core") {
        because("improved test assertions")
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn("irGenerateExternalsIntegrated")
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.test.get().kotlin)
}

val testLegacyJar by tasks.registering(Jar::class) {
    dependsOn("legacyTestClasses")
    archiveClassifier.set("tests")
    from(kotlin.js().compilations["test"].output.allOutputs)
}

val testIrJar by tasks.registering(Jar::class) {
    dependsOn("irTestClasses")
    archiveExtension.set("klib")
    archiveClassifier.set("tests")
    from(files("$buildDir/classes/kotlin/ir/test"))
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["testLegacyJar"]) {
                classifier = "tests"
            }
            artifact(tasks["testIrJar"]) {
                classifier = "tests"
            }
            pom {
                defaultPom()
            }
        }
    }
}

setupSigning()
setupPublication()
setupDokka()
