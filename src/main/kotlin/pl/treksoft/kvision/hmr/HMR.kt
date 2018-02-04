/**
 * @author Robert Jaros
 */
package pl.treksoft.kvision.hmr

/**
 * Helper variable for Hot Module Replacement (HMR).
 */
external val module: Module

/**
 * Helper interface for Hot Module Replacement (HMR).
 */
external interface Module {
    val hot: Hot?
}

/**
 * Helper interface for Hot Module Replacement (HMR).
 */
external interface Hot {
    val data: dynamic

    fun accept()
    fun accept(dependency: String, callback: () -> Unit)
    fun accept(dependencies: Array<String>, callback: (updated: Array<String>) -> Unit)

    fun dispose(callback: (data: dynamic) -> Unit)
}
