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
to download and integrate a number of NPM dependencies. To make upgrades easier, all of them are listed in 
the single **npm.dependencies** file, located in the root directory of the project. 
Currently this file contains:

    css-loader *
    style-loader *
    less *
    less-loader *
    imports-loader *
    uglifyjs-webpack-plugin *
    file-loader *
    url-loader *
    jquery 3.3.1
    bootstrap 3.3.7
    bootstrap-webpack 0.0.6
    font-awesome 4.7.0
    font-awesome-webpack 0.0.5-beta.2
    awesome-bootstrap-checkbox 0.3.7
    bootstrap-select 1.12.4
    ajax-bootstrap-select 1.4.3
    trix 0.11.1
    fecha 2.3.2
    bootstrap-datetime-picker 2.4.4
    bootstrap-touchspin 3.1.1
    snabbdom 0.6.9
    snabbdom-virtualize 0.7.0
    navigo 7.0.0
    jquery-resizable-dom 0.26.0

In case of an upgrade you should synchronize above content with the same [file](https://raw.githubusercontent.com/rjaros/kvision/master/npm.dependencies) 
located in the KVision source tree on GitHub.

NPM dependencies are used in your build.gradle with the following configuration:

    buildscript {
        ...
        ext.npmdeps = new File("npm.dependencies").getText()
        ...
    }
    ...
    kotlinFrontend {
        npm {
            npmdeps.eachLine { line ->
                def (name, version) = line.tokenize(" ")
                dependency(name, version)
            }
            devDependency("karma")
        }
        webpackBundle {
            bundleName = "main"
            contentPath = file('src/main/web')
        }
    }

See [KVision examples](https://github.com/rjaros/kvision-examples) for more details.