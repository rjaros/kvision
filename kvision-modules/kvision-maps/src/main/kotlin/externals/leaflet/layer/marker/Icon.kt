@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.marker

import externals.leaflet.geometry.Point
import externals.leaflet.layer.Layer
import org.w3c.dom.HTMLElement

/** Represents an icon to provide when creating a marker. */
open external class Icon<T : Icon.IconOptions>(options: IconOptions) : Layer<T> {

    open fun createIcon(oldIcon: HTMLElement = definedExternally): HTMLElement
    open fun createShadow(oldIcon: HTMLElement = definedExternally): HTMLElement

    interface DefaultIconOptions : IconOptions {
        var imagePath: String?
    }

    open class Default(options: DefaultIconOptions = definedExternally) : Icon<DefaultIconOptions> {

        companion object {
            var imagePath: String?
        }
    }

    interface IconOptions : LayerOptions {
        var iconUrl: String?
        var iconRetinaUrl: String?
        var iconSize: Point
        var iconAnchor: Point
        var popupAnchor: Point
        var tooltipAnchor: Point
        var shadowUrl: String?
        var shadowRetinaUrl: String?
        var shadowSize: Point
        var shadowAnchor: Point
        var className: String?
    }

}
