/**
 * @author Robert Jaros
 */
package pl.treksoft.kvision.hmr

/**
 * Base class for applications.
 *
 * Base class for applications supporting Hot Module Replacement (HMR).
 */
abstract class ApplicationBase {
    /**
     * Starting point for an application.
     * @param state Initial state between Hot Module Replacement (HMR).
     */
    abstract fun start(state: Map<String, Any>)

    /**
     * Ending point for an application.
     * @return final state for Hot Module Replacement (HMR).
     */
    abstract fun dispose(): Map<String, Any>
}
