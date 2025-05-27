@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
    }
}

rootProject.name = "kvision-project"

include(
    ":kvision",
    ":kvision-modules:kvision-common-types",
    ":kvision-modules:kvision-common-remote",
    ":kvision-modules:kvision-ballast",
    ":kvision-modules:kvision-bootstrap",
    ":kvision-modules:kvision-bootstrap-icons",
    ":kvision-modules:kvision-bootstrap-upload",
    ":kvision-modules:kvision-chart",
    ":kvision-modules:kvision-datetime",
    ":kvision-modules:kvision-fontawesome",
    ":kvision-modules:kvision-handlebars",
    ":kvision-modules:kvision-i18n",
    ":kvision-modules:kvision-imask",
    ":kvision-modules:kvision-jquery",
    ":kvision-modules:kvision-maps",
    ":kvision-modules:kvision-material",
    ":kvision-modules:kvision-pace",
    ":kvision-modules:kvision-print",
    ":kvision-modules:kvision-react",
    ":kvision-modules:kvision-redux-kotlin",
    ":kvision-modules:kvision-rest",
    ":kvision-modules:kvision-richtext",
    ":kvision-modules:kvision-routing-ballast",
    ":kvision-modules:kvision-routing-navigo",
    ":kvision-modules:kvision-routing-navigo-ng",
    ":kvision-modules:kvision-select-remote",
    ":kvision-modules:kvision-state",
    ":kvision-modules:kvision-state-flow",
    ":kvision-modules:kvision-tabulator",
    ":kvision-modules:kvision-tabulator-remote",
    ":kvision-modules:kvision-tailwindcss",
    ":kvision-modules:kvision-toastify",
    ":kvision-modules:kvision-tom-select",
    ":kvision-modules:kvision-tom-select-remote",
    ":kvision-modules:kvision-testutils",
    ":kvision-tools:kvision-gradle-plugin",
    ":kvision-assets",
)
