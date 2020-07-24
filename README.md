![KVision Logo](graphics/kvision-logo.png?raw=true "KVision")
# KVision

Object oriented web framework for Kotlin/JS.

[![Travis CI](https://travis-ci.com/rjaros/kvision.svg?branch=master)](https://travis-ci.com/rjaros/kvision)
[![Download](https://api.bintray.com/packages/rjaros/kotlin/kvision/images/download.svg) ](https://bintray.com/rjaros/kotlin/kvision/_latestVersion)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Chat: on slack](https://img.shields.io/badge/chat-on%20slack-green.svg)](https://kotlinlang.slack.com/?redir=%2Fmessages%2FCL4C1SLKC)

KVision allows you to build modern web applications with the [Kotlin](https://kotlinlang.org) language, 
without any use of HTML, CSS or JavaScript.

KVision prefers the imperative style of programming. It's object oriented and supports many well known OOP design patterns. It gives you a hierarchy of many different components, 
which are used as a builder blocks for the application GUI.

KVision's design is quite similar to many non-web UI programming libraries including Swing, JavaFX, QT, WinForms and Flutter.

KVision contains innovative connectivity interface for [Ktor](https://ktor.io/), [Jooby](https://jooby.io), [Spring Boot](https://spring.io/projects/spring-boot), 
[Javalin](https://javalin.io) and [Vert.x](https://vertx.io) frameworks on the server side, which
allows to build fullstack applications with shared common code.

**KVision is being actively developed. Please create an issue for any bugs or feature requests.**

## Features

- 100% type safe and fully compiled dev environment.
- Type safe DSL builders.
- Based on [Bootstrap](https://getbootstrap.com/) styles, typography and components.
- Utilizes [Snabbdom](https://github.com/snabbdom/snabbdom) fast virtual DOM implementation.
- Integrates with a lot of libraries and components:
    - [Font awesome](https://fontawesome.com/)
    - [Bootstrap Select](https://github.com/silviomoreto/bootstrap-select) (with [AJAX](https://github.com/truckingsim/Ajax-Bootstrap-Select) extension)
    - [Awesome Bootstrap Checkbox](https://github.com/flatlogic/awesome-bootstrap-checkbox)
    - [Trix editor](https://trix-editor.org/)
    - [Bootstrap Datetime picker](https://github.com/pingcheng/bootstrap4-datetimepicker)
    - [Bootstrap touchspin](https://github.com/istvan-ujjmeszaros/bootstrap-touchspin)
    - [Bootstrap File Input](http://plugins.krajee.com/file-input)
    - [Bootstrap Typeahead](https://github.com/eduardoinf/Bootstrap-3-Typeahead)
    - [Handlebars](http://handlebarsjs.com/)
    - [Chart.js](https://www.chartjs.org/)
    - [Tabulator](http://tabulator.info/)
    - [Redux](https://redux.js.org/)
    - [ReduxKotlin](https://reduxkotlin.org/)
    - [Navigo](https://github.com/krasimir/navigo)
    - [Moment.js](https://momentjs.com/)
    - [Pace](https://github.hubspot.com/pace/docs/welcome/)
    - [Leaflet](https://leafletjs.com/)
    - [Toastr](https://codeseven.github.io/toastr/)
 
- Includes sophisticated layout containers, including CSS flexbox, CSS grid and Bootstrap responsive 12 columns grid.
- Includes convenient forms implementation, with support for many different input components and easy to use validation.
- Supports [React](https://reactjs.org/) components with KVision DSL and built-in state management.
- Full support for [Onsen UI](https://onsen.io/) mobile web components with type-safe Kotlin API and DSL builders.
- Supports an observer pattern for data bindings.
- Internationalization support based on [Jed](http://messageformat.github.io/Jed/) library and [gettext](https://www.gnu.org/software/gettext/) translations. 
- Easy to use Drag & Drop support.
- Support for jQuery animations and effects.
- Type-safe REST connectivity.
- Support for event Flows and StateFlow for observables.
- Innovative integration interface for [Ktor](https://ktor.io), [Jooby](https://jooby.io), [Spring Boot](https://spring.io/projects/spring-boot),
 [Javalin](https://javalin.io) and [Vert.x](https://vertx.io) frameworks on the server side,
including support for type-safe websockets connections.
- Support for building hybrid mobile applications with [Apache Cordova](https://cordova.apache.org/).
- Support for building cross-platform, desktop applications with [Electron](https://electronjs.org).
- KVision applications are built with [Gradle](https://gradle.org/) with support for Webpack's [Hot Module Replacement (HMR)](https://webpack.js.org/concepts/hot-module-replacement/) and
[Kotlin JavaScript DCE (dead code elimination)](https://kotlinlang.org/docs/reference/javascript-dce.html). Kotlin compiler plugin for Gradle is available to automatically generate 
boilerplate code for server-side interfaces.
- [Karma](https://karma-runner.github.io/) testing framework support.
- IDE support (IntelliJ IDEA Community Edition).

## Examples and documentation

Ready to explore, rich set of [KVision examples](https://github.com/rjaros/kvision-examples) is available in the separate project. 

See also the complete frontend implementation of [RealWorld example application](https://github.com/rjaros/kvision-realworld-example-app) and a [fullstack version](https://github.com/rjaros/kvision-realworld-example-app-fullstack) built with Spring Webflux and R2DBC.  

The comprehensive [KVision guide](https://kvision.gitbook.io/kvision-guide/) is published on GitBook. 

Full API documentation (KDoc) is available at [https://rjaros.github.io/kvision/api/](https://rjaros.github.io/kvision/api/).

You can also look at [KVision blog posts at dev.to](https://dev.to/t/kvision/latest) and you can talk with KVision 
developers on Kotlin Slack [#kvision](https://kotlinlang.slack.com/messages/kvision/) channel.

If you are interested in the documentation for KVision 1.x (based on Bootstrap 3), you can [find the guide here](https://kvision.gitbook.io/kvision-guide/v/kvision-1.x/) and the [API docs here](https://rjaros.github.io/kvision/api1/).

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

## Code samples

### Hello world

        root("root") {
            span("Hello world!")
        }

### Basic components interactions using type safe DSL builders

        root("root") {
            hPanel(spacing = 20, alignItems = FlexAlignItems.CENTER) {
                val label = span("Not yet clicked.")
                var count = 0
                button("Click me").onClick {
                    label.content = "You clicked the button ${++count} times."
                }
            }
        }

### Tab panel with JavaScript routing

        val firstPanel = Div("First")
        val secondPanel = Div("Second")
        val thirdPanel = Div("Third")

        root("root") {
            tabPanel {
                addTab("First", firstPanel, route = "/first")
                addTab("Second", secondPanel, route = "/second")
                addTab("Third", thirdPanel, route = "/third")
            }
        }

### Type safe forms

        @Serializable
        data class Model(val username: String? = null, val password: String? = null)

        root("root") {
            formPanel {
                add(Model::username, Text(label = "Username"), required = true)
                add(Model::password, Password(label = "Password"), required = true)
                add(Button("OK").onClick {
                    val data: Model = this@FormPanel.getData()
                    println("Username: ${data.username}")
                    println("Password: ${data.password}")
                })
            }
        }
        
### Observer design pattern

        data class Data(val text: String)
        
        val model = observableListOf(
            Data("One"),
            Data("Two"),
            Data("Three")
        )
        root("root") {
            dataContainer(model, { data, _, _ ->
                Span(data.text)
            }, HPanel(spacing = 10))
        }
        model.reverse()
