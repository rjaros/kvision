/**
 * @author Robert Jaros
 */
package pl.treksoft.kvision

/**
 * Base class for applications.
 *
 * Every application class should inherit from this abstract class.
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
