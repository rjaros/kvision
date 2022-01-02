@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker

import externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement

/** Represents an icon to provide when creating a marker. */
open external class Icon(options: BaseIconOptions) : Layer {

    open var options: dynamic // BaseIconOptions

    open fun createIcon(oldIcon: HTMLElement = definedExternally): HTMLElement
    open fun createShadow(oldIcon: HTMLElement = definedExternally): HTMLElement

    interface DefaultIconOptions : BaseIconOptions {
        var imagePath: String?
    }

    open class Default(options: DefaultIconOptions = definedExternally) : Icon {

        override var options: DefaultIconOptions

        companion object {
            var imagePath: String?
        }
    }
}
