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
package pl.treksoft.kvision

import org.w3c.dom.asList
import kotlin.browser.document

/**
 * Internal singleton object which initializes and configures KVision Bootstrap module.
 */
@Suppress("EmptyCatchBlock", "TooGenericExceptionCaught")
internal object KVManagerBootstrap {
    private val links = document.getElementsByTagName("link")
    private val bootstrapWebpack = try {
        val bootswatch = links.asList().find { it.getAttribute("href")?.contains("bootstrap.min.css") ?: false }
        require("bootstrap")
        if (bootswatch != null) {
            if (bootswatch.getAttribute("href")?.contains("/paper/") == true) {
                require("./css/paper.css")
            }
        } else {
            require("bootstrap/dist/css/bootstrap.min.css")
        }
        require("./css/style.css")
    } catch (e: Throwable) {
    }
    private val fontAwesomeWebpack = try {
        require("font-awesome-webpack-4")
    } catch (e: Throwable) {
    }
    private val awesomeBootstrapCheckbox = try {
        require("awesome-bootstrap-checkbox")
    } catch (e: Throwable) {
    }
    private val bootstrapVerticalTabsCss = try {
        require("bootstrap-vertical-tabs")
    } catch (e: Throwable) {
    }
}
