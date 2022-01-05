@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.core

open external class Class {
    companion object {
        fun extend(props: Any): Any /* Any & Any */
        fun include(props: Any): Any /* Any & Any */
        fun mergeOptions(props: Any): Any /* Any & Any */
        fun addInitHook(initHookFn: () -> Unit): Any /* Any & Any */
        fun addInitHook(methodName: String, vararg args: Any): Any /* Any & Any */
    }
}
