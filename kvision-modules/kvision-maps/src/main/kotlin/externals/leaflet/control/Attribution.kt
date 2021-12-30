@file:JsModule("leaflet")
@file:JsNonModule
@file:JsQualifier("Control")

package externals.leaflet.control


open external class Attribution(
    options: AttributionOptions = definedExternally
) : Control {

    override var options: AttributionOptions

    open fun setPrefix(prefix: String): Attribution /* this */
    open fun setPrefix(prefix: Boolean): Attribution /* this */
    open fun addAttribution(text: String): Attribution /* this */
    open fun removeAttribution(text: String): Attribution /* this */

}
