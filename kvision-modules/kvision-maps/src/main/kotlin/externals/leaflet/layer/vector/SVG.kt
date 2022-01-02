@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.leaflet.geometry.Point
import org.w3c.dom.svg.SVGElement

open external class SVG(options: RendererOptions = definedExternally) : Renderer {
    companion object {
        /** @param[name] The name of an [SVG element](https://developer.mozilla.org/en-US/docs/Web/SVG/Element),
         * for example `line` or `circle` */
        fun create(name: String): SVGElement
        /**
         * Generates an SVG path string for multiple rings, with each ring turning into `M..L..L..`
         * instructions.
         */
        fun pointsToPath(
            rings: Array<Point /* Point | JsTuple<Number, Number> */>,
            closed: Boolean
        ): String
    }
}
