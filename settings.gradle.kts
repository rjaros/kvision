pluginManagement {
    repositories {
        mavenCentral()
        jcenter()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://dl.bintray.com/rjaros/kotlin") }
        mavenLocal()
    }
    resolutionStrategy {
        eachPlugin {
            when {
                requested.id.id == "kotlinx-serialization" -> useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
            }
        }
    }
}
rootProject.name = "kvision"

include(
    "kvision-modules:kvision-common-annotations",
    "kvision-modules:kvision-common-types",
    "kvision-modules:kvision-common-remote",
    "kvision-modules:kvision-bootstrap",
    "kvision-modules:kvision-bootstrap-css",
    "kvision-modules:kvision-bootstrap-datetime",
    "kvision-modules:kvision-bootstrap-dialog",
    "kvision-modules:kvision-bootstrap-select",
    "kvision-modules:kvision-bootstrap-select-remote",
    "kvision-modules:kvision-bootstrap-spinner",
    "kvision-modules:kvision-bootstrap-typeahead",
    "kvision-modules:kvision-bootstrap-typeahead-remote",
    "kvision-modules:kvision-bootstrap-upload",
    "kvision-modules:kvision-chart",
    "kvision-modules:kvision-cordova",
    "kvision-modules:kvision-datacontainer",
    "kvision-modules:kvision-electron",
    "kvision-modules:kvision-event-flow",
    "kvision-modules:kvision-fontawesome",
    "kvision-modules:kvision-handlebars",
    "kvision-modules:kvision-i18n",
    "kvision-modules:kvision-maps",
    "kvision-modules:kvision-moment",
    "kvision-modules:kvision-pace",
    "kvision-modules:kvision-redux",
    "kvision-modules:kvision-redux-kotlin",
    "kvision-modules:kvision-richtext",
    "kvision-modules:kvision-tabulator",
    "kvision-modules:kvision-tabulator-remote",
    "kvision-modules:kvision-server-javalin",
    "kvision-modules:kvision-server-jooby",
    "kvision-modules:kvision-server-ktor",
    "kvision-modules:kvision-server-spring-boot",
    "kvision-modules:kvision-testutils",
    "kvision-tools:kvision-compiler-plugin",
    "kvision-tools:kvision-gradle-plugin"
)
