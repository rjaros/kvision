plugins {
    kotlin("multiplatform") apply false
    `kotlin-dsl` apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.npm.publish) apply false
    alias(libs.plugins.nmcp) apply false
    alias(libs.plugins.nmcp.aggregation)
    id("org.jetbrains.dokka")
    id("maven-publish")
}

val versionVal = libs.versions.kvision.get()

allprojects {
    group = "io.kvision"
    if (hasProperty("SNAPSHOT")) {
        version = "$versionVal-SNAPSHOT"
    } else {
        version = versionVal
    }
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        lockFileDirectory = project.rootDir.resolve(".kotlin-js-store")
        resolution("zzz-kvision-assets", libs.versions.npm.kvision.assets.get())
        resolution("css-loader", libs.versions.css.loader.get())
        resolution("style-loader", libs.versions.style.loader.get())
        resolution("imports-loader", libs.versions.imports.loader.get())
        resolution("fecha", libs.versions.fecha.get())
        resolution("snabbdom", libs.versions.snabbdom.asProvider().get())
        resolution("@rjaros/snabbdom-virtualize", libs.versions.snabbdom.virtualize.get())
        resolution("split.js", libs.versions.splitjs.get())
        resolution("gettext.js", libs.versions.gettext.js.get())
        resolution("gettext-extract", libs.versions.gettext.extract.get())
        resolution("karma-junit-reporter", libs.versions.karma.junit.reporter.get())
        resolution("@popperjs/core", libs.versions.popperjs.core.get())
        resolution("bootstrap", libs.versions.bootstrap.asProvider().get())
        resolution("bootstrap-icons", libs.versions.bootstrap.icons.get())
        resolution("bootstrap-fileinput", libs.versions.bootstrap.fileinput.get())
        resolution("chart.js", libs.versions.chartjs.get())
        resolution("@eonasdan/tempus-dominus", libs.versions.tempus.dominus.get())
        resolution("@fortawesome/fontawesome-free", libs.versions.fontawesome.get())
        resolution("handlebars", libs.versions.handlebars.asProvider().get())
        resolution("handlebars-loader", libs.versions.handlebars.loader.get())
        resolution("imask", libs.versions.imask.get())
        resolution("jquery", libs.versions.jquery.asProvider().get())
        resolution("leaflet", libs.versions.leaflet.get())
        resolution("geojson", libs.versions.geojson.get())
        resolution("@material/web", libs.versions.material.web.get())
        resolution("pace-progressbar", libs.versions.pace.progressbar.get())
        resolution("print-js", libs.versions.printjs.get())
        resolution("react", libs.versions.react.get())
        resolution("react-dom", libs.versions.react.get())
        resolution("trix", libs.versions.trix.get())
        resolution("tabulator-tables", libs.versions.tabulator.get())
        resolution("toastify-js", libs.versions.toastify.get())
        resolution("tom-select", libs.versions.tom.select.get())
        resolution("postcss", libs.versions.postcss.asProvider().get())
        resolution("postcss-loader", libs.versions.postcss.loader.get())
        resolution("tailwindcss", libs.versions.tailwindcss.get())
        resolution("@tailwindcss/postcss", libs.versions.tailwindcss.get())
        resolution("cssnano", libs.versions.cssnano.get())
    }
}

nmcpAggregation {
    centralPortal {
        username = findProperty("mavenCentralUsername")?.toString()
        password = findProperty("mavenCentralPassword")?.toString()
        publishingType = "USER_MANAGED"
    }
}

dependencies {
    nmcpAggregation(project(":kvision"))
    nmcpAggregation(project(":kvision-modules:kvision-ballast"))
    nmcpAggregation(project(":kvision-modules:kvision-bootstrap"))
    nmcpAggregation(project(":kvision-modules:kvision-bootstrap-icons"))
    nmcpAggregation(project(":kvision-modules:kvision-bootstrap-upload"))
    nmcpAggregation(project(":kvision-modules:kvision-chart"))
    nmcpAggregation(project(":kvision-modules:kvision-common-remote"))
    nmcpAggregation(project(":kvision-modules:kvision-common-types"))
    nmcpAggregation(project(":kvision-modules:kvision-datetime"))
    nmcpAggregation(project(":kvision-modules:kvision-fontawesome"))
    nmcpAggregation(project(":kvision-modules:kvision-handlebars"))
    nmcpAggregation(project(":kvision-modules:kvision-i18n"))
    nmcpAggregation(project(":kvision-modules:kvision-imask"))
    nmcpAggregation(project(":kvision-modules:kvision-jquery"))
    nmcpAggregation(project(":kvision-modules:kvision-maps"))
    nmcpAggregation(project(":kvision-modules:kvision-material"))
    nmcpAggregation(project(":kvision-modules:kvision-pace"))
    nmcpAggregation(project(":kvision-modules:kvision-print"))
    nmcpAggregation(project(":kvision-modules:kvision-react"))
    nmcpAggregation(project(":kvision-modules:kvision-redux-kotlin"))
    nmcpAggregation(project(":kvision-modules:kvision-rest"))
    nmcpAggregation(project(":kvision-modules:kvision-richtext"))
    nmcpAggregation(project(":kvision-modules:kvision-routing-ballast"))
    nmcpAggregation(project(":kvision-modules:kvision-routing-navigo"))
    nmcpAggregation(project(":kvision-modules:kvision-routing-navigo-ng"))
    nmcpAggregation(project(":kvision-modules:kvision-select-remote"))
    nmcpAggregation(project(":kvision-modules:kvision-state"))
    nmcpAggregation(project(":kvision-modules:kvision-state-flow"))
    nmcpAggregation(project(":kvision-modules:kvision-tabulator"))
    nmcpAggregation(project(":kvision-modules:kvision-tabulator-remote"))
    nmcpAggregation(project(":kvision-modules:kvision-tailwindcss"))
    nmcpAggregation(project(":kvision-modules:kvision-testutils"))
    nmcpAggregation(project(":kvision-modules:kvision-toastify"))
    nmcpAggregation(project(":kvision-modules:kvision-tom-select"))
    nmcpAggregation(project(":kvision-modules:kvision-tom-select-remote"))
    nmcpAggregation(project(":kvision-tools:kvision-gradle-plugin"))
    dokka(project(":kvision"))
    dokka(project(":kvision-modules:kvision-ballast"))
    dokka(project(":kvision-modules:kvision-bootstrap"))
    dokka(project(":kvision-modules:kvision-bootstrap-icons"))
    dokka(project(":kvision-modules:kvision-bootstrap-upload"))
    dokka(project(":kvision-modules:kvision-chart"))
    dokka(project(":kvision-modules:kvision-common-remote"))
    dokka(project(":kvision-modules:kvision-common-types"))
    dokka(project(":kvision-modules:kvision-datetime"))
    dokka(project(":kvision-modules:kvision-fontawesome"))
    dokka(project(":kvision-modules:kvision-handlebars"))
    dokka(project(":kvision-modules:kvision-i18n"))
    dokka(project(":kvision-modules:kvision-imask"))
    dokka(project(":kvision-modules:kvision-jquery"))
    dokka(project(":kvision-modules:kvision-maps"))
    dokka(project(":kvision-modules:kvision-material"))
    dokka(project(":kvision-modules:kvision-pace"))
    dokka(project(":kvision-modules:kvision-print"))
    dokka(project(":kvision-modules:kvision-react"))
    dokka(project(":kvision-modules:kvision-redux-kotlin"))
    dokka(project(":kvision-modules:kvision-rest"))
    dokka(project(":kvision-modules:kvision-richtext"))
    dokka(project(":kvision-modules:kvision-routing-ballast"))
    dokka(project(":kvision-modules:kvision-routing-navigo"))
    dokka(project(":kvision-modules:kvision-routing-navigo-ng"))
    dokka(project(":kvision-modules:kvision-select-remote"))
    dokka(project(":kvision-modules:kvision-state"))
    dokka(project(":kvision-modules:kvision-state-flow"))
    dokka(project(":kvision-modules:kvision-tabulator"))
    dokka(project(":kvision-modules:kvision-tabulator-remote"))
    dokka(project(":kvision-modules:kvision-tailwindcss"))
    dokka(project(":kvision-modules:kvision-testutils"))
    dokka(project(":kvision-modules:kvision-toastify"))
    dokka(project(":kvision-modules:kvision-tom-select"))
    dokka(project(":kvision-modules:kvision-tom-select-remote"))
}
