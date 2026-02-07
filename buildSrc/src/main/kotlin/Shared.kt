import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.tasks.DokkaGeneratePublicationTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.net.URI

fun KotlinMultiplatformExtension.compilerOptions() {
    targets.configureEach { target ->
        target.compilations.configureEach { compilation ->
            compilation.compileTaskProvider.configure {
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
                it.useKarma {
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
        it.languageVersion.set(JavaLanguageVersion.of(target))
    }
    jvm {
        compilations.configureEach { compilation ->
            compilation.compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xjsr305=strict")
                }
            }
        }
    }
}

fun KotlinJvmProjectExtension.kotlinJvmTargets(target: String = "21") {
    jvmToolchain {
        it.languageVersion.set(JavaLanguageVersion.of(target))
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
        it.license {
            it.name.set("MIT")
            it.url.set("https://opensource.org/licenses/MIT")
        }
    }
    developers {
        it.developer {
            it.id.set("rjaros")
            it.name.set("Robert Jaros")
            it.organization.set("Treksoft")
            it.organizationUrl.set("http://www.treksoft.pl")
        }
    }
    scm {
        it.url.set(kvisionVcsUrl)
        it.connection.set("scm:git:git://github.com/rjaros/kvision.git")
        it.developerConnection.set("scm:git:git://github.com/rjaros/kvision.git")
    }
}

fun Project.setupPublication() {
    val isSnapshot = hasProperty("SNAPSHOT")
    extensions.getByType(PublishingExtension::class.java).run {
        publications.withType(MavenPublication::class.java).all {
            if (!isSnapshot) it.artifact(this@setupPublication.tasks.getByName("javadocJar"))
            it.pom {
                it.defaultPom()
            }
        }
    }
    extensions.getByType(SigningExtension::class.java).run {
        if (!isSnapshot) {
            sign(extensions.getByType(PublishingExtension::class.java).publications)
        }
    }
    // Workaround https://github.com/gradle/gradle/issues/26091
    tasks.withType(AbstractPublishToMaven::class.java).configureEach {
        val signingTasks = tasks.withType(Sign::class.java)
        it.mustRunAfter(signingTasks)
    }
}

fun Project.setupDokka(provider: TaskProvider<DokkaGeneratePublicationTask>, mdPath: String = "../../", modulesPath: String = "kvision-modules/") {
    tasks.register("javadocJar", Jar::class.java) {
        it.dependsOn(provider)
        it.from(provider.map { it.outputDirectory })
        it.archiveClassifier.set("javadoc")
    }
    extensions.getByType(DokkaExtension::class.java).run {
        dokkaSourceSets.configureEach {
            it.includes.from("${mdPath}Module.md")
            it.sourceLink {
                it.localDirectory.set(projectDir.resolve("src"))
                it.remoteUrl.set(URI("https://github.com/rjaros/kvision/tree/master/${modulesPath}${project.name}/src"))
                it.remoteLineSuffix.set("#L")
            }
        }
    }
}
