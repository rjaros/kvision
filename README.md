# KVision

Object oriented Web UI framework for Kotlin/JS.

KVision allows you to build user interface of modern web applications with the [Kotlin](https://kotlinlang.org) language.
It's designed to be object oriented in the "most classical" sense - it gives you a hierarchy of many different components,
which are used to build application UI.

Unlike most of the popular web UI frameworks (AngularJS, React, Vue.js and others),
KVision is not declarative - it is not designed to mix HTML code (or pseudo code) with a typical
programming language like JavaScript. In KVision everything is just written in Kotlin, and your code can be reused not by creating any templates,
but by using well known OOP design patterns - composition and inheritance.

This design is quite similar to many non-web UI programming libraries including Swing (Java), QT (C++) and WinForms (C#).

**KVision is a new project in a development phase. Please create an issue for any bugs or feature requests.**

## Features

- 100% type safe and fully compiled dev environment.
- Based on [Bootstrap](https://getbootstrap.com/) styles, typography and components.
- Utilizes [Snabbdom](https://github.com/snabbdom/snabbdom) fast virtual DOM implementation.
- Integrates with libraries and components from [Font awesome](https://fontawesome.com/), [Bootstrap Select](https://github.com/silviomoreto/bootstrap-select) (with [AJAX](https://github.com/truckingsim/Ajax-Bootstrap-Select) extension),
[Awesome Bootstrap Checkbox](https://github.com/flatlogic/awesome-bootstrap-checkbox), [Trix editor](https://trix-editor.org/), [Bootstrap Datetime picker](https://github.com/AuspeXeu/bootstrap-datetimepicker), 
[Bootstrap touchspin](https://github.com/istvan-ujjmeszaros/bootstrap-touchspin) and [Navigo](https://github.com/krasimir/navigo).
- Includes sophisticated layout containers, including CSS flexbox, CSS grid and Bootstrap responsive 12 columns grid.
- Includes convenient forms implementation, with support for many different input components and easy to use validation.
- Data binding support for [observable](https://github.com/rjaros/kotlin-observable-js) data model.
- Ready to explore [KVision examples](https://github.com/rjaros/kvision-examples) are available,
built with [Gradle](https://gradle.org/) and supporting Webpack's [Hot Module Replacement (HMR)](https://webpack.js.org/concepts/hot-module-replacement/).
- [Karma](https://karma-runner.github.io/) testing framework support.
- IDE support (IntelliJ IDEA Community Edition).

## Getting started

1. Download [KVision examples](https://github.com/rjaros/kvision-examples) from GitHub:

        git clone https://github.com/rjaros/kvision-examples.git
        
2. Enter one of the examples directory:

        cd kvision-examples/showcase                        (on Linux)
        cd kvision-examples\showcase                        (on Windows)

3. Run Gradle incremental build with:

        ./gradlew -t run                                    (on Linux)
        gradlew.bat -t run                                  (on Windows)
        
4. Open [http://localhost:8088/](http://localhost:8088/) in your browser.

## Tutorial

{{TBD}}

## API documentation

Full API documentation is available at [https://rjaros.github.io/kvision/api/](https://rjaros.github.io/kvision/api/).
