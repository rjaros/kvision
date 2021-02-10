import de.marcphilipp.gradle.nexus.NexusPublishExtension
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

fun Project.repositories() {
    repositories {
        mavenCentral()
        jcenter()
        mavenLocal()
    }
}

fun KotlinJsProjectExtension.kotlinJsTargets() {
    js {
        kotlinJsTargets()
    }
}

fun KotlinMultiplatformExtension.kotlinJsTargets() {
    js {
        kotlinJsTargets()
    }
}

private fun KotlinJsTargetDsl.kotlinJsTargets() {
    compilations.all {
        kotlinOptions {
            moduleKind = "umd"
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

fun Project.setupSigning() {
    extensions.getByType<SigningExtension>().run {
        sign(extensions.getByType<PublishingExtension>().publications)
    }
}

fun Project.setupPublication() {
    extensions.getByType<PublishingExtension>().run {
        publications.withType<MavenPublication>().all {
            pom {
                defaultPom()
            }
        }
        extensions.getByType<NexusPublishExtension>().run {
            repositories {
                sonatype {
                    username.set(findProperty("ossrhUsername")?.toString())
                    password.set(findProperty("ossrhPassword")?.toString())
                }
            }
        }
    }
}
