plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val jqueryKotlinVersion: String by project
val kotestVersion: String by project

kotlin {
    kotlinJsTargets()
    sourceSets {
        val jsMain by getting {
            dependencies {
                api("io.kvision:jquery-kotlin:$jqueryKotlinVersion")
                api(rootProject)
                api(kotlin("test-js"))

                implementation(project.dependencies.platform("io.kotest:kotest-bom:$kotestVersion"))
                implementation("io.kotest:kotest-assertions-core") {
                    because("improved test assertions")
                }
            }
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka/html"))

}

setupSigning()
setupPublication()
setupDokkaMpp()
