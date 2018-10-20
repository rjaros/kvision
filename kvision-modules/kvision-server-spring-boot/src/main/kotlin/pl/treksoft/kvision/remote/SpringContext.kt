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
package pl.treksoft.kvision.remote

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class SpringContext : ApplicationContextAware {

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        CONTEXT = applicationContext
    }

    companion object {

        private var CONTEXT: ApplicationContext? = null

        /**
         * Get a Spring bean by type.
         */
        fun <T> getBean(beanClass: Class<T>): T {
            return CONTEXT!!.getBean(beanClass)
        }

        /**
         * Get a Spring bean by name.
         */
        fun getBean(beanName: String): Any {
            return CONTEXT!!.getBean(beanName)
        }

        /**
         * Get a Spring bean by name and type.
         */
        fun <T> getBean(beanName: String, beanClass: Class<T>): T {
            return CONTEXT!!.getBean(beanName, beanClass)
        }
    }

}
