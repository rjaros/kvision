package pl.treksoft.kvision

external val module: Module

external interface Module {
    val hot: Hot?
}

external interface Hot {
    val data: dynamic

    fun accept()
    fun accept(dependency: String, callback: () -> Unit)
    fun accept(dependencies: Array<String>, callback: (updated: Array<String>) -> Unit)

    fun dispose(callback: (data: dynamic) -> Unit)
}

external fun require(name: String): dynamic
