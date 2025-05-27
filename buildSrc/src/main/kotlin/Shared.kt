import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.tasks.DokkaGeneratePublicationTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.net.URI

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
        useEsModules()
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        compilerOptions {
            target.set("es2015")
        }
    }
}

fun KotlinMultiplatformExtension.kotlinJvmTargets(target: String = "21") {
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

fun KotlinJvmProjectExtension.kotlinJvmTargets(target: String = "21") {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(target))
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

fun Project.setupPublication() {
    val isSnapshot = hasProperty("SNAPSHOT")
    extensions.getByType<PublishingExtension>().run {
        publications.withType<MavenPublication>().all {
            if (!isSnapshot) artifact(tasks["javadocJar"])
            pom {
                defaultPom()
            }
        }
    }
    extensions.getByType<SigningExtension>().run {
        if (!isSnapshot) {
            sign(extensions.getByType<PublishingExtension>().publications)
        }
    }
    // Workaround https://github.com/gradle/gradle/issues/26091
    tasks.withType<AbstractPublishToMaven>().configureEach {
        val signingTasks = tasks.withType<Sign>()
        mustRunAfter(signingTasks)
    }
}

fun Project.setupDokka(provider: TaskProvider<DokkaGeneratePublicationTask>, mdPath: String = "../../", modulesPath: String = "kvision-modules/") {
    tasks.register<Jar>("javadocJar") {
        dependsOn(provider)
        from(provider.flatMap { it.outputDirectory })
        archiveClassifier.set("javadoc")
    }
    extensions.getByType<DokkaExtension>().run {
        dokkaSourceSets.invoke {
            configureEach {
                includes.from("${mdPath}Module.md")
                sourceLink {
                    localDirectory.set(projectDir.resolve("src"))
                    remoteUrl.set(URI("https://github.com/rjaros/kvision/tree/master/${modulesPath}${project.name}/src"))
                    remoteLineSuffix.set("#L")
                }
            }
        }
    }
}
