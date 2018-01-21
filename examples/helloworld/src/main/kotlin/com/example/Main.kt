package com.example

import pl.treksoft.kvision.ApplicationBase
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.module
import kotlin.browser.document

external fun require(name: String): dynamic

fun main(args: Array<String>) {
    var application: ApplicationBase? = null

    val state: dynamic = module.hot?.let { hot ->
        hot.accept()

        hot.dispose { data ->
            data.appState = application?.dispose()
            KVManager.shutdown()
            application = null
        }

        hot.data
    }

    if (document.body != null) {
        application = start(state)
    } else {
        KVManager.init()
        application = null
        document.addEventListener("DOMContentLoaded", { application = start(state) })
    }
}

fun start(state: dynamic): ApplicationBase? {
    if (document.getElementById("helloworld") == null) return null
    val application = Helloworld()
    @Suppress("UnsafeCastFromDynamic")
    application.start(state?.appState ?: emptyMap())
    return application
}

