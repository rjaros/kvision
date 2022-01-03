@file:JsModule("leaflet")
@file:JsNonModule

package externals.leaflet.layer.vector

import externals.leaflet.layer.Layer
import externals.leaflet.layer.vector.Renderer.RendererOptions

/**
 * Base class for vector renderer implementations ([SVG], [Canvas]). Handles the DOM container of
 * the renderer, its bounds, and its zoom animation.
 *
 * A [Renderer] works as an implicit layer group for all [Path]s - the renderer itself can be added
 * or removed to the map. All paths use a renderer, which can be implicit (the map will decide
 * the type of renderer and use it automatically) or explicit (using the renderer option of the
 * path).
 *
 * Do not use this class directly, use [SVG] and [Canvas] instead.
 */
abstract external class Renderer(
    options: RendererOptions = definedExternally
) : Layer<RendererOptions> {

    interface RendererOptions : LayerOptions {
        var padding: Number?
        var tolerance: Number?
    }

}
