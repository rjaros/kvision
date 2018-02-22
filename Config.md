### Project configuration

KVision applications are built with [Gradle](https://gradle.org/).
The necessary artifacts are published on [Bintray](https://bintray.com/) so you have to add some repositories in your build.gradle:

    repositories {
        jcenter()
        maven { url = 'https://dl.bintray.com/gbaldeck/kotlin' }
        maven { url = 'https://dl.bintray.com/rjaros/kotlin' }
    }

Next add dependencies on Kotlin and KVision:

    dependencies {
        compile "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlin_version"
        compile "org.jetbrains.kotlin:kotlin-test-js:$kotlin_version"
        compile "pl.treksoft:kvision:$kvision_version"
    }

KVision applications are using [Kotlin frontend plugin](https://github.com/Kotlin/kotlin-frontend-plugin) 
to download and integrate a number of NPM dependencies. Some of them are listed in 
a separate **npm.dependencies** file, located in the root directory of the project. 
Currently this file contains:

    bootstrap 3.3.7
    bootstrap-webpack 0.0.6
    bootstrap-select 1.12.4
    ajax-bootstrap-select 1.4.3
    bootstrap-datetime-picker 2.4.4
    bootstrap-touchspin 3.1.1
    font-awesome 4.7.0
    font-awesome-webpack 0.0.5-beta.2
    awesome-bootstrap-checkbox 0.3.7
    trix 0.11.1

These are optional dependencies and can be removed if the corresponding features are not used in your KVision application.
This can make the generated JavaScript code much smaller.

See [KVision examples](https://github.com/rjaros/kvision-examples) for more details.