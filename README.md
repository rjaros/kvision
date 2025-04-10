![KVision Logo](graphics/kvision-logo.png?raw=true "KVision")
# KVision

Object oriented web framework for Kotlin/JS.

[https://kvision.io](https://kvision.io)

![CI](https://github.com/rjaros/kvision/workflows/CI/badge.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.kvision/kvision/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.kvision/kvision)
[![API](https://img.shields.io/badge/API-dokka-green)](https://rjaros.github.io/kvision/index.html)
[![IR](https://img.shields.io/badge/Kotlin%2FJS-IR%20supported-yellow)](https://kotl.in/jsirsupported)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Slack](https://img.shields.io/badge/slack-channel-green?logo=slack)](https://kotlinlang.slack.com/?redir=%2Fmessages%2FCL4C1SLKC)
[![Discord](https://img.shields.io/discord/880183907641851964?label=Discord&logo=discord&logoColor=white)](https://discord.gg/SdsN3arN2w)

KVision allows you to build modern web applications with the [Kotlin](https://kotlinlang.org) language,
without any use of HTML, CSS or JavaScript. It gives you a rich hierarchy of ready to use GUI components,
which can be used as builder blocks for the application UI.

KVision fully supports both reactive and imperative programming models. It gives you everything you may need for the state management of your apps.

KVision integrates with [Kilua RPC](https://github.com/rjaros/kilua-rpc) library for [Ktor](https://ktor.io/), [Jooby](https://jooby.io), [Spring Boot](https://spring.io/projects/spring-boot),
[Javalin](https://javalin.io), [Vert.x](https://vertx.io) and [Micronaut](https://micronaut.io) frameworks on the server side, which
allows to build fullstack applications with shared code for data model and business logic.

KVision is being actively developed. Please create an issue for any bugs or feature requests.

## Sample code

```kotlin
class App : Application() {

    val state = ObservableValue("Hello world")

    override fun start() {
        root("root") {
            vPanel {
                h1(state) {
                    +it
                }
                button("Add an exclamation mark").onClick {
                    state.value += "!"
                }
            }
        }
    }
}
```

## Features

- 100% type safe and fully compiled dev environment.
- Type safe DSL builders.
- Based on [Bootstrap](https://getbootstrap.com/) styles, typography and components.
- Utilizes [Snabbdom](https://github.com/snabbdom/snabbdom) fast virtual DOM implementation.
- Integrates with a lot of libraries and components:
    - [Font awesome](https://fontawesome.com/)
    - [Tom Select](https://tom-select.js.org/)
    - [Trix editor](https://trix-editor.org/)
    - [Tempus Dominus datetime picker](https://github.com/Eonasdan/tempus-dominus)
    - [Bootstrap File Input](http://plugins.krajee.com/file-input)
    - [Handlebars](http://handlebarsjs.com/)
    - [Chart.js](https://www.chartjs.org/)
    - [Tabulator](http://tabulator.info/)
    - [ReduxKotlin](https://reduxkotlin.org/)
    - [Ballast](https://copper-leaf.github.io/ballast/)
    - [Navigo](https://github.com/krasimir/navigo)
    - [Pace](https://github.hubspot.com/pace/docs/welcome/)
    - [Leaflet](https://leafletjs.com/)
    - [Toastify JS](https://apvarun.github.io/toastify-js/)
    - [Print.js](https://printjs.crabbly.com/)
    - [Imask.js](https://imask.js.org/)
    - [Material Web](https://material-web.dev/)

- Includes sophisticated layout containers, including CSS flexbox, CSS grid and Bootstrap responsive 12 columns grid.
- Includes convenient forms implementation, with support for many different input components and easy to use validation.
- Support for observer pattern, data binding, event Flows and StateFlow for observables.
- Supports [React](https://reactjs.org/) components with KVision DSL and built-in state management.
- Internationalization support based on [gettext](https://www.gnu.org/software/gettext/) translations and [gettext.js](https://github.com/guillaumepotier/gettext.js) library.
- Easy to use Drag & Drop support.
- Support for jQuery animations and effects.
- Type-safe REST connectivity.
- Dark mode support.
- KVision applications are built with [Gradle](https://gradle.org/) with support for Webpack's [Hot Module Replacement (HMR)](https://webpack.js.org/concepts/hot-module-replacement/) and
[Kotlin JavaScript DCE (dead code elimination)](https://kotlinlang.org/docs/reference/javascript-dce.html).
- [Karma](https://karma-runner.github.io/) testing framework support.
- IDE support (IntelliJ IDEA). The [KVision Project Wizard](https://github.com/JakubNeukirch/kvision-project-wizard) is being developed as a separate project (thanks to [@JakubNeukirch](https://github.com/JakubNeukirch)).

## Examples and documentation

Ready to explore, rich set of [KVision examples](https://github.com/rjaros/kvision-examples) is available in the separate project.

See also the complete frontend implementation of [RealWorld example application](https://github.com/rjaros/kvision-realworld-example-app) and a [fullstack version](https://github.com/rjaros/kvision-realworld-example-app-fullstack) built with Spring Webflux and R2DBC.

The comprehensive [KVision guide](https://kvision.gitbook.io/kvision-guide/) is published on GitBook.

The API documentation, generated with new Dokka, is available at [https://rjaros.github.io/kvision/index.html](https://rjaros.github.io/kvision/index.html).

You can also look at [KVision blog posts at dev.to](https://dev.to/t/kvision/latest) and you can talk with KVision
users and developers on Kotlin Slack [#kvision](https://kotlinlang.slack.com/messages/kvision/) channel and on the [Discord server](https://discord.gg/SdsN3arN2w).

## Quickstart

#### Development

1. Download [KVision examples](https://github.com/rjaros/kvision-examples) from GitHub:

        git clone https://github.com/rjaros/kvision-examples.git

2. Enter one of the examples directory:

        cd kvision-examples/showcase                        (on Linux)
        cd kvision-examples\showcase                        (on Windows)

3. Run Gradle incremental build with:

        ./gradlew -t run                                    (on Linux)
        gradlew.bat -t run                                  (on Windows)

4. Open [http://localhost:3000/](http://localhost:3000/) in your browser.

5. Play with the code and see your changes immediately in the browser.

#### Production

To build complete application optimized for production run:

        ./gradlew zip                       (on Linux)
        gradlew.bat zip                     (on Windows)

Application package will be saved as build/libs/showcase-1.0.0-SNAPSHOT.zip.

## Leave us a star

If you like this project, please give it a star on GitHub. Thank you!
