rootProject.name = "kvision-gradle-plugin-test"

pluginManagement {
  includeBuild("../../")
}

@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {

  repositories {
    mavenCentral()
    gradlePluginPortal()
  }

  pluginManagement {
    repositories {
      gradlePluginPortal()
      mavenCentral()
    }
  }

}
