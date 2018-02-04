package pl.treksoft.kvision.routing

import pl.treksoft.navigo.Navigo

open class Routing : Navigo(null, true, "#!") {

    companion object {
        fun start() {
            routing = Routing()
        }

        fun shutdown() {
            routing.destroy()
        }
    }
}

var routing = Routing()
