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

package io.kvision.ktml

import io.kvision.core.Container
import io.kvision.core.KVScope
import io.kvision.html.Div
import io.kvision.i18n.I18n
import io.kvision.snabbdom.VNode
import kotlinx.coroutines.launch

/**
 * A KTML template component.
 *
 * @constructor
 * @param name the template name
 * @param namesByLanguage the template names for different languages
 * @param parameters the template parameters
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class KtmlTemplate(
    name: String?,
    namesByLanguage: Map<String, String>? = null,
    parameters: Map<String, Any>?,
    className: String? = null,
    init: (KtmlTemplate.() -> Unit)? = null
) : Div(rich = true, className = className) {

    /**
     * Template name.
     */
    var name by refreshOnUpdate(name) {
        loadContent()
    }

    /**
     * Template names for different languages.
     */
    var namesByLanguage by refreshOnUpdate(namesByLanguage) {
        loadContent()
    }

    /**
     * Template parameters.
     */
    var parameters by refreshOnUpdate(parameters) {
        loadContent()
    }

    /**
     * Template parameters for different languages.
     */
    var parametersByLanguage: Map<String, Map<String, Any>>? by refreshOnUpdate(null) {
        loadContent()
    }

    /**
     * Current template name
     */
    protected var currentName: String? = null

    /**
     * Current template parameters
     */
    protected var currentParameters: Map<String, Any>? = null

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
        loadContent()
    }

    protected fun loadContent() {
        val ktmlEngine = Ktml.ktmlEngine
            ?: throw IllegalStateException("Ktml is not initialized. Please call Ktml.init(KtmlRegistry) before using ktml() function.")
        currentName = namesByLanguage?.get(I18n.language) ?: name
        currentParameters = parametersByLanguage?.get(I18n.language) ?: parameters
        if (currentName != null && currentParameters != null) {
            KVScope.launch {
                content = ktmlEngine.renderPage(currentName!!, currentParameters!!)
            }
        } else {
            content = null
        }
    }

    override fun render(): VNode {
        if (currentName != null && currentParameters != null && lastLanguage != null && lastLanguage != I18n.language) {
            loadContent()
        }
        return super.render()
    }
}

/**
 * DSL builder extension function.
 *
 * @param name the template name
 * @param parameters the template parameters
 * @param className CSS class names
 * @param init an initializer extension function
 * @return the created [KtmlTemplate]
 */
fun Container.ktmlTemplate(
    name: String,
    parameters: Map<String, Any> = emptyMap(),
    className: String? = null,
    init: (KtmlTemplate.() -> Unit)? = null
): KtmlTemplate {
    val ktmlTemplate = KtmlTemplate(name, null, parameters, className, init)
    this.add(ktmlTemplate)
    return ktmlTemplate
}

/**
 * DSL builder extension function.
 *
 * @param namesByLanguage the template names for different languages
 * @param parameters the template parameters
 * @param className CSS class names
 * @param init an initializer extension function
 * @return the created [KtmlTemplate]
 */
fun Container.ktmlTemplate(
    namesByLanguage: Map<String, String>,
    parameters: Map<String, Any> = emptyMap(),
    className: String? = null,
    init: (KtmlTemplate.() -> Unit)? = null
): KtmlTemplate {
    val ktmlTemplate = KtmlTemplate(null, namesByLanguage, parameters, className, init)
    this.add(ktmlTemplate)
    return ktmlTemplate
}
