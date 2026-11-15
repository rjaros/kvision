/*
 * Copyright (c) 2025 Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.kvision.maps

import io.kvision.maps.externals.leaflet.geo.CRS
import io.kvision.maps.externals.leaflet.geo.LatLng
import io.kvision.maps.externals.leaflet.geo.LatLngBounds
import io.kvision.maps.externals.leaflet.layer.Layer
import io.kvision.maps.externals.leaflet.layer.vector.Renderer
import io.kvision.maps.externals.leaflet.map.LeafletMap.LeafletMapOptions
import io.kvision.utils.obj

/**
 * Options for the Leaflet Map.
 */
data class MapsOptions(
    val attributionControl: Boolean? = null,
    val zoomControl: Boolean? = null,
    val closePopupOnClick: Boolean? = null,
    val zoomSnap: Int? = null,
    val zoomDelta: Double? = null,
    val trackResize: Boolean? = null,
    val boxZoom: Boolean? = null,
    val doubleClickZoom: dynamic = null,
    val dragging: Boolean? = null,
    val crs: CRS? = null,
    val center: LatLng? = null,
    val zoom: Int? = null,
    val maxZoom: Int? = null,
    val minZoom: Int? = null,
    val layers: Array<Layer<*>>? = null,
    val maxBounds: LatLngBounds? = null,
    val renderer: Renderer<Renderer.RendererOptions>? = null,
    val zoomAnimation: Boolean? = null,
    val zoomAnimationThreshold: Double? = null,
    val fadeAnimation: Boolean? = null,
    val markerZoomAnimation: Boolean? = null,
    val transform3DLimit: Double? = null,
    val inertia: Boolean? = null,
    val inertiaDeceleration: Double? = null,
    val inertiaMaxSpeed: Double? = null,
    val easeLinearity: Double? = null,
    val worldCopyJump: Boolean? = null,
    val maxBoundsViscosity: Double? = null,
    val keyboard: Boolean? = null,
    val keyboardPanDelta: Double? = null,
    val ariaKeyShortcuts: dynamic = null,
    val translate: ((text: String) -> String?)? = null,
    val scrollWheelZoom: dynamic = null,
    val wheelDebounceTime: Double? = null,
    val wheelPxPerZoomLevel: Double? = null,
    val tapHold: Boolean? = null,
    val tapTolerance: Double? = null,
    val pinchZoom: dynamic = null,
    val bounceAtZoomLimits: Boolean? = null,
)

internal fun MapsOptions.toJs(): LeafletMapOptions = obj {
    if (this@toJs.attributionControl != null) this.attributionControl = this@toJs.attributionControl
    if (this@toJs.zoomControl != null) this.zoomControl = this@toJs.zoomControl
    if (this@toJs.closePopupOnClick != null) this.closePopupOnClick = this@toJs.closePopupOnClick
    if (this@toJs.zoomSnap != null) this.zoomSnap = this@toJs.zoomSnap
    if (this@toJs.zoomDelta != null) this.zoomDelta = this@toJs.zoomDelta
    if (this@toJs.trackResize != null) this.trackResize = this@toJs.trackResize
    if (this@toJs.boxZoom != null) this.boxZoom = this@toJs.boxZoom
    if (this@toJs.doubleClickZoom != null) this.doubleClickZoom = this@toJs.doubleClickZoom
    if (this@toJs.dragging != null) this.dragging = this@toJs.dragging
    if (this@toJs.crs != null) this.crs = this@toJs.crs
    if (this@toJs.center != null) this.center = this@toJs.center
    if (this@toJs.zoom != null) this.zoom = this@toJs.zoom
    if (this@toJs.maxZoom != null) this.maxZoom = this@toJs.maxZoom
    if (this@toJs.minZoom != null) this.minZoom = this@toJs.minZoom
    if (this@toJs.layers != null) this.layers = this@toJs.layers
    if (this@toJs.maxBounds != null) this.maxBounds = this@toJs.maxBounds
    if (this@toJs.renderer != null) this.renderer = this@toJs.renderer
    if (this@toJs.zoomAnimation != null) this.zoomAnimation = this@toJs.zoomAnimation
    if (this@toJs.zoomAnimationThreshold != null) this.zoomAnimationThreshold = this@toJs.zoomAnimationThreshold
    if (this@toJs.fadeAnimation != null) this.fadeAnimation = this@toJs.fadeAnimation
    if (this@toJs.markerZoomAnimation != null) this.markerZoomAnimation = this@toJs.markerZoomAnimation
    if (this@toJs.transform3DLimit != null) this.transform3DLimit = this@toJs.transform3DLimit
    if (this@toJs.inertia != null) this.inertia = this@toJs.inertia
    if (this@toJs.inertiaDeceleration != null) this.inertiaDeceleration = this@toJs.inertiaDeceleration
    if (this@toJs.inertiaMaxSpeed != null) this.inertiaMaxSpeed = this@toJs.inertiaMaxSpeed
    if (this@toJs.easeLinearity != null) this.easeLinearity = this@toJs.easeLinearity
    if (this@toJs.worldCopyJump != null) this.worldCopyJump = this@toJs.worldCopyJump
    if (this@toJs.maxBoundsViscosity != null) this.maxBoundsViscosity = this@toJs.maxBoundsViscosity
    if (this@toJs.keyboard != null) this.keyboard = this@toJs.keyboard
    if (this@toJs.keyboardPanDelta != null) this.keyboardPanDelta = this@toJs.keyboardPanDelta
    if (this@toJs.ariaKeyShortcuts != null) this.ariaKeyShortcuts = this@toJs.ariaKeyShortcuts
    if (this@toJs.translate != null) this.translate = this@toJs.translate
    if (this@toJs.scrollWheelZoom != null) this.scrollWheelZoom = this@toJs.scrollWheelZoom
    if (this@toJs.wheelDebounceTime != null) this.wheelDebounceTime = this@toJs.wheelDebounceTime
    if (this@toJs.wheelPxPerZoomLevel != null) this.wheelPxPerZoomLevel = this@toJs.wheelPxPerZoomLevel
    if (this@toJs.tapHold != null) this.tapHold = this@toJs.tapHold
    if (this@toJs.tapTolerance != null) this.tapTolerance = this@toJs.tapTolerance
    if (this@toJs.pinchZoom != null) this.pinchZoom = this@toJs.pinchZoom
    if (this@toJs.bounceAtZoomLimits != null) this.bounceAtZoomLimits = this@toJs.bounceAtZoomLimits
}
