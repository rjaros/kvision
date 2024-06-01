import io.github.gradlenexus.publishplugin.NexusPublishExtension
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.repositories() {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

fun KotlinMultiplatformExtension.compilerOptions() {
    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                    freeCompilerArgs.add("-Xdont-warn-on-error-suppression")
                }
            }
        }
    }
}

fun KotlinMultiplatformExtension.kotlinJsTargets() {
    js(IR) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
}

fun KotlinMultiplatformExtension.kotlinJvmTargets(target: String = "17") {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(target))
    }
    jvm {
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xjsr305=strict")
                }
            }
        }
    }
}

val kvisionProjectName = "KVision"
val kvisionProjectDescription = "Object oriented web framework for Kotlin/JS"
val kvisionProjectWebsite = "https://github.com/rjaros/kvision"
val kvisionVcsUrl = "https://github.com/rjaros/kvision.git"

fun MavenPom.defaultPom() {
    name.set(kvisionProjectName)
    description.set(kvisionProjectDescription)
    url.set(kvisionProjectWebsite)
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
        url.set(kvisionVcsUrl)
        connection.set("scm:git:git://github.com/rjaros/kvision.git")
        developerConnection.set("scm:git:git://github.com/rjaros/kvision.git")
    }
}

fun Project.setupSigning() {
    extensions.getByType<SigningExtension>().run {
        isRequired = !project.hasProperty("SNAPSHOT")
        if (isRequired) {
            sign(extensions.getByType<PublishingExtension>().publications)
        }
    }
}

fun Project.setupPublication(withJvm: Boolean = false, withSigning: Boolean = false) {
    extensions.getByType<PublishingExtension>().run {
        publications.withType<MavenPublication>().all {
            if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
            pom {
                defaultPom()
            }
        }
        if (withSigning) {
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
    afterEvaluate {
        if (withJvm) {
            tasks.getByName("publishKotlinMultiplatformPublicationToSonatypeRepository") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication", "signJvmPublication")
            }
            tasks.getByName("publishJsPublicationToSonatypeRepository") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication", "signJvmPublication")
            }
            tasks.getByName("publishJvmPublicationToSonatypeRepository") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication", "signJvmPublication")
            }
            // Only for publishing signed artifacts to local maven repository
            /*
            tasks.getByName("publishKotlinMultiplatformPublicationToMavenLocal") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication", "signJvmPublication")
            }
            tasks.getByName("publishJsPublicationToMavenLocal") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication", "signJvmPublication")
            }
            tasks.getByName("publishJvmPublicationToMavenLocal") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication", "signJvmPublication")
            }
             */
        } else {
            tasks.getByName("publishKotlinMultiplatformPublicationToSonatypeRepository") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication")
            }
            tasks.getByName("publishJsPublicationToSonatypeRepository") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication")
            }
            // Only for publishing signed artifacts to local maven repository
            /*
            tasks.getByName("publishKotlinMultiplatformPublicationToMavenLocal") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication")
            }
            tasks.getByName("publishJsPublicationToMavenLocal") {
                dependsOn("signKotlinMultiplatformPublication", "signJsPublication")
            }
             */
        }
    }
}

fun Project.setupDokka() {
    tasks.named<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml").configure {
        dokkaSourceSets.invoke {
            named("main") {
                suppress.set(true)
            }
            register("kvision") {
                includes.from("../../Module.md")
                displayName.set("jvm")
                platform.set(org.jetbrains.dokka.Platform.jvm)
                includeNonPublic.set(false)
                skipDeprecated.set(false)
                reportUndocumented.set(false)
                sourceRoots.from(file("src/main/kotlin"))
            }
        }
    }
}

fun Project.setupDokkaMpp(withJvm: Boolean = false) {
    tasks.named<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml").configure {
        dokkaSourceSets.invoke {
            named("commonMain") {
                suppress.set(true)
            }
            named("jsMain") {
                suppress.set(true)
            }
            if (findByName("jvmMain") != null) {
                named("jvmMain") {
                    suppress.set(true)
                }
            }
            register("kvision-common") {
                includes.from("../../Module.md")
                displayName.set("common")
                platform.set(org.jetbrains.dokka.Platform.common)
                includeNonPublic.set(false)
                skipDeprecated.set(false)
                reportUndocumented.set(false)
                sourceRoots.from(file("src/commonMain/kotlin"))
            }
            register("kvision-js") {
                includes.from("../../Module.md")
                displayName.set("js")
                platform.set(org.jetbrains.dokka.Platform.js)
                includeNonPublic.set(false)
                skipDeprecated.set(false)
                reportUndocumented.set(false)
                sourceRoots.from(file("src/jsMain/kotlin"))
            }
            if (withJvm) { // Workaround for StackOverflowError in Spring Boot module
                register("kvision-jvm") {
                    includes.from("../../Module.md")
                    displayName.set("jvm")
                    platform.set(org.jetbrains.dokka.Platform.jvm)
                    includeNonPublic.set(false)
                    skipDeprecated.set(false)
                    reportUndocumented.set(false)
                    sourceRoots.from(file("src/jvmMain/kotlin"))
                }
            }
        }
    }
}
