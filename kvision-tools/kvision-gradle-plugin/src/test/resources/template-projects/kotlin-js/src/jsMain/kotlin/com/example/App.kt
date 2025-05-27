package com.example

import io.kvision.Application
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapModule
import io.kvision.CoreModule
import io.kvision.Hot
import io.kvision.html.div
import io.kvision.i18n.DefaultI18nManager
import io.kvision.i18n.I18n
import io.kvision.i18n.I18n.tr
import io.kvision.panel.root
import io.kvision.startApplication
import io.kvision.utils.useModule

@JsModule("/kotlin/modules/css/kvapp.css")
external val kvappCss: dynamic

@JsModule("/kotlin/modules/i18n/messages-en.json")
external val messagesEn: dynamic

@JsModule("/kotlin/modules/i18n/messages-pl.json")
external val messagesPl: dynamic

class App : Application() {
    init {
        useModule(kvappCss)
    }

    override fun start() {
        I18n.manager =
            DefaultI18nManager(
                mapOf(
                    "en" to messagesEn,
                    "pl" to messagesPl
                )
            )

        root("kvapp") {
            div(tr("This is a localized message."))
        }
    }
}

fun main() {
    startApplication(::App, js("import.meta.webpackHot").unsafeCast<Hot?>(), BootstrapModule, BootstrapCssModule, CoreModule)
}
