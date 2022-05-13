import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.html.span
import io.kvision.module
import io.kvision.panel.root
import io.kvision.startApplication

class App : Application() {

    override fun start() {
        root("kvapp") {
            span("Hello world!")
        }
    }
}

fun main() {
    startApplication(::App, module.hot, CoreModule)
}
