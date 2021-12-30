@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import org.w3c.dom.svg.SVGElement

open external class SVG(options: RendererOptions = definedExternally) : Renderer {
    companion object {
        fun <K : String> create(name: K): Any
        fun create(name: String): SVGElement
        fun pointsToPath(rings: Array<Any /* Point | JsTuple<Number, Number> */>, close: Boolean): String
    }
}
