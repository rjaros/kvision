import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.KotlinJsTarget

fun Project.repositories() {
    repositories {
        mavenCentral()
        jcenter()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
        maven {
            url = uri("https://dl.bintray.com/gbaldeck/kotlin")
            metadataSources {
                mavenPom()
                artifact()
            }
        }
        maven { url = uri("https://dl.bintray.com/rjaros/kotlin") }
        mavenLocal()
    }
}

fun KotlinJsProjectExtension.kotlinJsTargets() {
    target {
        val isProductionBuild = project.extra.get("production") as Boolean
        kotlinJsTargets(isProductionBuild)
    }
}

fun KotlinMultiplatformExtension.kotlinJsTargets() {
    js {
        val isProductionBuild = project.extra.get("production") as Boolean
        kotlinJsTargets(isProductionBuild)
    }
}

private fun KotlinJsTarget.kotlinJsTargets(isProductionBuild: Boolean) {
    compilations.all {
        kotlinOptions {
            moduleKind = "umd"
            sourceMap = !isProductionBuild
            if (!isProductionBuild) {
                sourceMapEmbedSources = "always"
            }
        }
    }
    browser {
        testTask {
            useKarma {
                useChromeHeadless()
            }
        }
    }
}

fun KotlinMultiplatformExtension.kotlinJvmTargets() {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-Xjsr305=strict")
            }
        }
    }
}

fun MavenPom.defaultPom() {
    name.set("KVision")
    description.set("Object oriented web framework for Kotlin/JS")
    url.set("https://github.com/rjaros/kvision")
    licenses {
        license {
            name.set("MIT")
            url.set("https://opensource.org/licenses/MIT")
        }
    }
    developers {
        developer {
            id.set("rjaros")
            name.set("Robert Jaros")
            organization.set("Treksoft")
            organizationUrl.set("http://www.treksoft.pl")
        }
    }
    scm {
        url.set("https://github.com/rjaros/kvision.git")
        connection.set("scm:git:git://github.com/rjaros/kvision.git")
        developerConnection.set("scm:git:git://github.com/rjaros/kvision.git")
    }
}

fun Project.setupPublication() {
    plugins.apply("maven-publish")

    extensions.getByType<PublishingExtension>().run {
        publications.withType<MavenPublication>().all {
            pom {
                defaultPom()
            }
        }

        repositories {
            maven {
                url = uri("https://api.bintray.com/maven/rjaros/kotlin/${project.name}/;publish=0;override=1")
                credentials {
                    username = findProperty("buser")?.toString()
                    password = findProperty("bkey")?.toString()
                }
            }
        }
    }
}
