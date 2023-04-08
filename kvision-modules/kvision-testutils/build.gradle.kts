plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val jqueryKotlinVersion: String by project
val kotestVersion: String by project

kotlin {
    kotlinJsTargets()
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

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from("$buildDir/dokka/html")
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
        }
    }
}

setupSigning()
setupPublication()
setupDokka()
