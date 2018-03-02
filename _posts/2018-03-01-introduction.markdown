---
layout: post
title:  "Introduction"
author: Robert Jaros
date:   2018-03-01 14:50:53 +0100
categories: kvision kotlin
---

This is my first article about [KVision](https://github.com/rjaros/kvision) -  a new open source Web UI framework written in Kotlin language.
I plan to write a series of them - describing what KVision is and how to use it. I will try to explain the design, focus on the 
unique features and demonstrate the usage examples.

### Why new framework?

There are of course many different front-end frameworks on the market. A lot of them share many similarities, others offer some special and unique features. 
Probably most of them are plain JavaScript frameworks, but there are other languages and technologies in play as well. 
Just look at [TodoMVC](http://todomvc.com) website to find them and learn about them.

I've been working with different technologies and frameworks for many years. I've used them in many commercial and non-commercial projects of different size and complexity.
Most of the time I was more then satisfied with the tools I've been using. But I've never found a solution, I could just call perfect. There was always something missing 
and something was not fully correct.

I don't really like to go with the mainstream. I like to explore and learn new possibilities. I try to make things easier and more productive. I like to reuse and integrate good stuff, 
made by other people available in open source. I had many thoughts and conclusions about features, a perfect framework should have.
And finally I've decided to make my own framework. I plan to make it a perfect solution for me - it's quite easy when you know the expectations ;-) 
But I hope it can be a good solutions for a few other developers as well.

### Main features

#### Compiled and strongly typed programming language

When you work on a small project, dynamic and loosely typed programming language (like JavaScript) is more then OK.
But when the project becomes big and complex, and more developers are working with it, you just need to have more control over the code. You have to be able
to find bugs as soon as possible. You have to be able to refactor a large portions of code. It is possible only with compiled languages.
There are a few such languages in use for web development today - e.g. TypeScript or Scala. And Kotlin is just at the beginning of its way in this
direction. In fact, KVision project started as "SVision" written with [Scala.js](https://www.scala-js.org/), but soon I've decided to change the language to Kotlin,
because of much better IDE support, simpler design and better compilation time.

#### Object oriented, imperative design

Unlike most of the popular web frameworks (AngularJS, React, Vue.js and others),
KVision is not declarative - it is not designed to mix HTML code (or pseudo code) with a typical
programming language. You can write KVision application without any knowledge of HTML or CSS, 
but at the same time you are able to use this knowledge if you wish.

In KVision everything is just written in Kotlin. Your whole application is designed as type-safe and compiled. 
KVision gives you a hierarchy of consistent components, which you can just use in your project or which you can extend to 
modify their default behavior. You reuse you code by well known OOP design patterns - composition and inheritance.

This design is well known in the UI programming world. In fact, it's probably used everywhere but in the web environment.
Swing, JavaFX, QT and WinForms are clear and solid examples of object oriented UI libraries and KVision goes the same way.

#### Ready to use components

Many frameworks give you well designed architecture but lack ready to use components. You have to
search the Internet to find ways to use rich text editor, advanced select box, date picker or even 
a simple tab panel. 

KVision contains:

- sophisticated containers (tabs, stack, dock, grid, horizontal, vertical, flexbox, responsive),
- a bunch of text input components including rich text editor
- buttons, checboxes and radios
- date and time picker
- spinner
- advanced select box with ajax support
- data binding components
- modals
- floating, resizable windows

All of them with modern, [Bootstrap](https://getbootstrap.com/) based look & feel, and a consistent behavior.

#### Forms support

Almost all non trivial applications use some kind of forms. But implementing form support in not only about
showing form controls on the screen. Form items are bound to some data model, and this model should be type-safe.
You would like your text inputs to be "Strings", but you would also like your date values to be of "Date" type, 
your spinner value to be "Number" and your checkbox state to be "Boolean". KVision lets you do it in an easy
and consistent way. And additionally it allows you to define and test validation for single fields or for the complete forms.

#### Suitable for any application

KVision was designed to be open and flexible. By default it gives you [Bootstrap](https://getbootstrap.com/) based look & feel, 
but it can be disabled if you wish. Then you can design your application appearance from the scratch, and you are limited only
by your own knowledge of CSS.

Ability to implement a fully compatible [TodoMVC](http://todomvc.com) 
application, which you can find in the [KVision-examples](https://github.com/rjaros/kvision-examples) repository, 
proves that KVision is suitable for any kind of projects, including responsive, mobile web applications or even a simple, plain websites.

#### Last but not least

Other KVision features:

- Type safe DSL builders
- [Font awesome](https://fontawesome.com/) icons support
- Drag & drop support
- Integrated JS router
- [Karma](https://karma-runner.github.io/) testing framework support
- Full IDE support with IntelliJ IDEA Community Edition

make this framework a complete solution ready for many different use cases. I will go into details of these features
in the following articles.

### Get started

1. Download [KVision examples](https://github.com/rjaros/kvision-examples) from GitHub:

        git clone https://github.com/rjaros/kvision-examples.git
        
2. Enter "template" example directory:

        cd kvision-examples/template                        (on Linux)
        cd kvision-examples\template                        (on Windows)

3. Run Gradle incremental build with:

        ./gradlew -t run                                    (on Linux)
        gradlew.bat -t run                                  (on Windows)
        
4. Open [http://localhost:8088/](http://localhost:8088/) in your browser.

5. Import "template" project in IntelliJ IDEA and open src/main/kotlin/com/example/App.kt file. 
You can of course use your favourite text editor.

6. Add some code inside the **start** function:

        override fun start(state: Map<String, Any>) {
            Root("kvapp") {
                label("Hello world!")
            }
        }

7. See your changes immediately in the browser.

### Learn more

- Explore all [KVision examples](https://github.com/rjaros/kvision-examples)
- Read other articles at [KVision website](https://rjaros.github.io/kvision/)
- Check [API Documentation](https://rjaros.github.io/kvision/api/)
- Fork and play with the [source code](https://github.com/rjaros/kvision)
