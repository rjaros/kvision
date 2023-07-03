/*
 * Copyright (c) 2017-present Robert Jaros
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
package io.kvision.panel

import io.kvision.Application
import io.kvision.KVManager
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Style
import io.kvision.core.Widget
import io.kvision.snabbdom.VNode
import io.kvision.snabbdom.h
import io.kvision.utils.snClasses
import io.kvision.utils.snOpt
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList

/**
 * Root container types.
 */
enum class ContainerType(val type: String) {
    NONE(""),
    FIXED("container"),
    FLUID("container-fluid"),
    SM("container-sm"),
    MD("container-md"),
    LG("container-lg"),
    XL("container-xl"),
    XXL("container-xxl")
}

/**
 * Root container.
 *
 * This container is bound to the specific element in the main HTML file of the project.
 * It is always the root of components tree and it is responsible for rendering and updating
 * Snabbdom virtual DOM.
 */
@Suppress("TooManyFunctions")
class Root : SimplePanel {

    private val containerType: ContainerType
    private val addRow: Boolean
    private val contextMenus: MutableList<Widget> = mutableListOf()
    private var rootVnode: VNode? = null
    private var elementName: String? = null

    internal var singleRenderers = 0

    private var asyncBuffer: MutableList<() -> Unit> = mutableListOf()
    private var asyncTimer: Int? = null

    private var stylesCached: String? = null
    private val isFirstRoot = roots.isEmpty()

    /**
     * Sets the root container to the synchronous mode. Should be used for tests only.
     */
    var synchronousMode = false

    /**
     * Disables rendering of the component tree under this root.
     */
    var disableRendering = false
        set(value) {
            field = value
            if (!disableRendering) reRender()
        }

    /**
     * @constructor
     * @param id ID attribute of element in the main HTML file
     * @param containerType Bootstrap container type
     * @param addRow if true, a <div class="row"> element is rendered inside the root
     *        container (default is based on container type)
     * @param init an initializer extension function
     */
    constructor(
        id: String,
        containerType: ContainerType = ContainerType.NONE,
        addRow: Boolean = containerType != ContainerType.FIXED && containerType != ContainerType.NONE,
        init: (Root.() -> Unit)? = null
    ) : super() {
        this.containerType = containerType
        this.addRow = addRow
        val element = document.getElementById(id)
        if (element != null) {
            getPropertiesFromElement(element)
            rootVnode = KVManager.patch(id, this.renderVNode())
        }
        this.id = id
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * @constructor
     * @param element HTML element in the DOM tree
     * @param containerType Bootstrap container type
     * @param addRow if true, a <div class="row"> element is rendered inside the root
     *        container (default is based on container type)
     * @param init an initializer extension function
     */
    constructor(
        element: HTMLElement,
        containerType: ContainerType = ContainerType.NONE,
        addRow: Boolean = containerType != ContainerType.FIXED && containerType != ContainerType.NONE,
        init: (Root.() -> Unit)? = null
    ) : super() {
        this.containerType = containerType
        this.addRow = addRow
        getPropertiesFromElement(element)
        rootVnode = KVManager.patch(element, this.renderVNode())
        this.id = "kv_root_${counter++}"
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    init {
        roots.add(this)
        if (isFirstRoot) {
            modals.forEach { it.parent = this }
        }
    }

    private fun getPropertiesFromElement(element: Element) {
        elementName = element.nodeName
        element.attributes.asList().forEach {
            if (it.name != "id") {
                this.setAttribute(it.name, it.value)
            }
        }
        element.classList.asList().forEach {
            this.addCssClass(it)
        }
    }

    override fun render(): VNode {
        return if (addRow) {
            render("$elementName#$id", stylesVNodes() + arrayOf(h("div", snOpt {
                `class` = snClasses(listOf("row" to true))
            }, childrenVNodes())) + modalsVNodes() + contextMenusVNodes())
        } else {
            render("$elementName#$id", stylesVNodes() + childrenVNodes() + modalsVNodes() + contextMenusVNodes())
        }
    }

    fun addContextMenu(contextMenu: Widget) {
        contextMenus.add(contextMenu)
        contextMenu.parent = this
        this.setInternalEventListener<Root> {
            click = { e ->
                @Suppress("UnsafeCastFromDynamic")
                if (!e.asDynamic().dropDownCM) contextMenu.hide()
            }
        }
        refresh()
    }

    private fun stylesVNodes(): Array<VNode> {
        return if (isFirstRoot) {
            if (Style.styles.isNotEmpty()) {
                val stylesDesc = if (stylesCached != null) {
                    stylesCached!!
                } else {
                    val groupByMediaQuery = Style.styles.groupBy { it.mediaQuery }
                    stylesCached = groupByMediaQuery.map { (media, styles) ->
                        if (media == null) {
                            styles.joinToString("\n") { it.generateStyle() }
                        } else {
                            "@media ($media) {\n" + styles.joinToString("\n") { it.generateStyle() } + "\n}"
                        }
                    }.joinToString("\n\n")
                    stylesCached!!
                }
                arrayOf(h("style", arrayOf("\n$stylesDesc\n")))
            } else {
                arrayOf()
            }
        } else {
            arrayOf()
        }
    }

    private fun modalsVNodes(): Array<VNode> {
        return if (isFirstRoot) {
            modals.filter { it.visible }.map { it.renderVNode() }.toTypedArray()
        } else {
            arrayOf()
        }
    }

    private fun contextMenusVNodes(): Array<VNode> {
        return contextMenus.filter { it.visible }.map { it.renderVNode() }.toTypedArray()
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (containerType != ContainerType.NONE) {
            classSetBuilder.add(containerType.type)
        }
    }

    internal fun reRender() {
        if (singleRenderers == 0 && !disableRendering && rootVnode != null) {
            rootVnode = KVManager.patch(rootVnode!!, renderVNode())
        }
    }

    override fun <T> singleRender(block: () -> T): T {
        singleRenderers++
        val result = block()
        singleRenderers--
        reRender()
        return result
    }

    override fun singleRenderAsync(block: () -> Unit) {
        if (synchronousMode) {
            singleRender {
                block()
            }
        } else {
            asyncBuffer.add(block)
            if (asyncTimer != null) {
                window.clearTimeout(asyncTimer!!)
            }
            asyncTimer = window.setTimeout({
                singleRender {
                    asyncBuffer.forEach { it() }
                    asyncBuffer.clear()
                }
                asyncTimer = null
            }, 0)
        }
    }

    /**
     * Restart the root component
     */
    fun restart() {
        if (rootVnode != null) {
            rootVnode = KVManager.patch(rootVnode!!, h("div"))
            rootVnode = KVManager.patch(rootVnode!!, renderVNode())
        }
    }

    internal fun clearStylesCache() {
        stylesCached = null
    }

    override fun getRoot(): Root {
        return this
    }

    override fun dispose() {
        super.dispose()
        roots.remove(this)
        if (isFirstRoot) {
            Style.styles.clear()
            modals.clear()
        }
    }

    companion object {
        internal var counter = 0
        private val modals: MutableList<Widget> = mutableListOf()

        /**
         * @suppress internal function
         */
        fun disposeAllRoots() {
            roots.forEach { it.dispose() }
            roots.clear()
        }

        /**
         * @suppress internal property
         */
        val roots: MutableList<Root> = mutableListOf()

        /**
         * @suppress internal function
         */
        fun getFirstRoot(): Root? {
            return if (roots.isNotEmpty())
                roots[0]
            else
                null
        }

        /**
         * @suppress internal function
         */
        fun getLastRoot(): Root? {
            return if (roots.isNotEmpty())
                roots[roots.size - 1]
            else
                null
        }

        /**
         * @suppress internal function
         */
        fun addModal(modal: Widget) {
            modals.add(modal)
        }

        /**
         * @suppress internal function
         */
        fun removeModal(modal: Widget) {
            modals.remove(modal)
        }
    }
}

/**
 * Create new Root container based on ID
 * @param id ID attribute of element in the main HTML file
 * @param containerType Bootstrap container type
 * @param addRow if true, a <div class="row"> element is rendered inside the root
 *               container (default is based on container type)
 * @param init an initializer extension function
 * @return the created Root container
 */
@Suppress("unused")
fun Application.root(
    id: String,
    containerType: ContainerType = ContainerType.NONE,
    addRow: Boolean = containerType != ContainerType.FIXED && containerType != ContainerType.NONE,
    init: (Root.() -> Unit)? = null
): Root {
    return Root(id, containerType, addRow, init)
}

/**
 * Create new Root container based on HTML element
 * @param element HTML element in the DOM tree
 * @param containerType Bootstrap container type
 * @param addRow if true, a <div class="row"> element is rendered inside the root
 *               container (default is based on container type)
 * @param init an initializer extension function
 * @return the created Root container
 */
@Suppress("unused")
fun Application.root(
    element: HTMLElement,
    containerType: ContainerType = ContainerType.NONE,
    addRow: Boolean = containerType != ContainerType.FIXED && containerType != ContainerType.NONE,
    init: (Root.() -> Unit)? = null
): Root {
    return Root(element, containerType, addRow, init)
}
