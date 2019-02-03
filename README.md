# KVision

Object oriented Web UI framework for Kotlin/JS.

KVision allows you to build user interface of modern web applications with the [Kotlin](https://kotlinlang.org) language.
It's designed to be object oriented in the "most classical" sense - it gives you a hierarchy of many different components,
which are used to build application UI.

Unlike most of the popular web UI frameworks (AngularJS, React, Vue.js and others),
KVision is not declarative - it is not designed to mix HTML code (or pseudo code) with a typical
programming language like JavaScript. In KVision everything is just written in Kotlin, and your code can be reused not by creating any templates,
but by using well known OOP design patterns - composition and inheritance.

This design is quite similar to many non-web UI programming libraries including Swing (Java), JavaFX, QT (C++) and WinForms (C#).

KVision contains innovative connectivity interface for [Ktor](https://ktor.io/), [Jooby](https://jooby.org) and [Spring Boot](https://spring.io/projects/spring-boot) frameworks on the server side, which
allows to build full-stack, multiplatform applications with shared common code.

**KVision is a new project in a development phase. Please create an issue for any bugs or feature requests.**

## Features

- 100% type safe and fully compiled dev environment.
- Type safe DSL builders.
- Based on [Bootstrap](https://getbootstrap.com/) styles, typography and components.
- Utilizes [Snabbdom](https://github.com/snabbdom/snabbdom) fast virtual DOM implementation.
- Integrates with libraries and components from [Font awesome](https://fontawesome.com/), [Bootstrap Select](https://github.com/silviomoreto/bootstrap-select) (with [AJAX](https://github.com/truckingsim/Ajax-Bootstrap-Select) extension),
[Awesome Bootstrap Checkbox](https://github.com/flatlogic/awesome-bootstrap-checkbox), [Trix editor](https://trix-editor.org/), [Bootstrap Datetime picker](https://github.com/smalot/bootstrap-datetimepicker), 
[Bootstrap touchspin](https://github.com/istvan-ujjmeszaros/bootstrap-touchspin), [Bootstrap File Input](http://plugins.krajee.com/file-input),
 [Handlebars](http://handlebarsjs.com/) and [Navigo](https://github.com/krasimir/navigo).
- Includes sophisticated layout containers, including CSS flexbox, CSS grid and Bootstrap responsive 12 columns grid.
- Includes convenient forms implementation, with support for many different input components and easy to use validation.
- Data binding support for [observable](https://github.com/rjaros/kotlin-observable-js) data model.
- Internationalization support based on [Jed](http://messageformat.github.io/Jed/) library and [gettext](https://www.gnu.org/software/gettext/) translations. 
- Easy to use Drag & Drop support.
- Innovative integration interface for [Ktor](https://ktor.io), [Jooby](https://jooby.org) and [Spring Boot](https://spring.io/projects/spring-boot) frameworks on the server side.
- Ready to explore [KVision examples](https://github.com/rjaros/kvision-examples) are available,
built with [Gradle](https://gradle.org/) and supporting Webpack's [Hot Module Replacement (HMR)](https://webpack.js.org/concepts/hot-module-replacement/) and
[Kotlin JavaScript DCE (dead code elimination)](https://kotlinlang.org/docs/reference/javascript-dce.html).
- [Karma](https://karma-runner.github.io/) testing framework support.
- IDE support (IntelliJ IDEA Community Edition).

## Documentation

The comprehensive [KVision guide](https://kvision.gitbook.io/kvision-guide/) is published on GitBook. It's not yet 100% complete, but it contains a lot of useful information about this framework. 

Full API documentation (KDoc) is available at [https://rjaros.github.io/kvision/api/](https://rjaros.github.io/kvision/api/).


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
        
4. Open [http://localhost:8088/](http://localhost:8088/) in your browser.

5. Play with the code and see your changes immediately in the browser.

#### Production

To build complete application optimized for production run:

        ./gradlew -Pprod=true distZip                       (on Linux)
        gradlew.bat -Pprod=true distZip                     (on Windows)
        
Application package will be saved as build/distributions/showcase.zip.

## Code samples

### Hello world

        val root = Root("root")
        val label = Label("Hello world!")
        root.add(label)

### Basic components interactions

##### Simple, explicit way

        val root = Root("root")
        val panel = HPanel(spacing = 20, alignItems = FlexAlignItems.CENTER)
        val label = Label("Not yet clicked.")
        panel.add(label)
        var count = 0
        val button = Button("Click me")
        button.onClick {
            count++
            label.content = "You clicked the button $count times."
        }
        panel.add(button)
        root.add(panel)

##### Using Kotlin language features

        Root("root").add(
            HPanel(spacing = 20, alignItems = FlexAlignItems.CENTER).apply {
                val label = Label("Not yet clicked.").also { add(it) }
                var count = 0
                add(Button("Click me").onClick {
                    label.content = "You clicked the button ${++count} times."
                })
            }
        )

##### Using type safe DSL builders

        Root("root") {
            hPanel(spacing = 20, alignItems = FlexAlignItems.CENTER) {
                val label = label("Not yet clicked.")
                var count = 0
                button("Click me") {
                    onClick {
                        label.content = "You clicked the button ${++count} times."
                    }
                }
            }
        }

### Tab panel with JavaScript routing

        val firstPanel = Tag(TAG.DIV, "First")
        val secondPanel = Tag(TAG.DIV, "Second")
        val thirdPanel = Tag(TAG.DIV, "Third")

        Root("root").add(TabPanel().apply {
            addTab("First", firstPanel, route = "/first")
            addTab("Second", secondPanel, route = "/second")
            addTab("Third", thirdPanel, route = "/third")
        })

### Type safe forms

        @Serializable
        data class Model(val username: String? = null, val password: String? = null)

        Root("root").add(FormPanel {
            add(Model::username, Text(label = "Username"), required = true)
            add(Model::password, Password(label = "Password"), required = true)
            add(Button("OK").onClick {
                val data: Model = this@FormPanel.getData()
                println("Username: ${data.username}")
                println("Password: ${data.password}")
            })
        })
        
### Data binding with observable data model

        data class Data(val text: String)
        
        val model = observableListOf(
            Data("One"),
            Data("Two"),
            Data("Three")
        )
        Root("root").add(DataContainer(model, { data, _, _ ->
            Label(data.text)
        }, HPanel(spacing = 10)))

        GlobalScope.launch { // Kotlin coroutines
            while (true) {
                delay(1000)
                model.reverse()
            }
        }

