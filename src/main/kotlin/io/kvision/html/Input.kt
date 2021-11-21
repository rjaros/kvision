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
package io.kvision.html

import io.kvision.snabbdom.VNode
import io.kvision.core.AttributeSetBuilder
import io.kvision.core.Container
import io.kvision.core.Widget

/**
 * Input types.
 */
enum class InputType(internal val type: String) {
    TEXT("text"),
    PASSWORD("password"),
    EMAIL("email"),
    TEL("tel"),
    COLOR("color"),
    SEARCH("search"),
    URL("url"),
    HIDDEN("hidden"),
    BUTTON("button"),
    CHECKBOX("checkbox"),
    DATE("date"),
    DATETIME_LOCAL("datetime-local"),
    FILE("file"),
    IMAGE("image"),
    MONTH("month"),
    NUMBER("number"),
    RADIO("radio"),
    RANGE("range"),
    RESET("reset"),
    SUBMIT("submit"),
    TIME("time"),
    WEEK("week"),
}

/**
 * Input types.
 */
enum class Autocomplete(val type: String) {
    OFF("off"),
    ON("on"),
    NAME("name"),
    HONORIFIC_PREFIX("honorific-prefix"),
    GIVEN_NAME("given-name"),
    ADDITIONAL_NAME("additional-name"),
    FAMILY_NAME("family-name"),
    HONORIFIC_SUFFIX("honorific-suffix"),
    NICKNAME("nickname"),
    USERNAME("username"),
    NEW_PASSWORD("new-password"),
    CURRENT_PASSWORD("current-password"),
    ONE_TIME_CODE("one-time-code"),
    ORGANIZATION_TITLE("organization-title"),
    ORGANIZATION("organization"),
    STREET_ADDRESS("street-address"),
    ADDRESS_LINE1("address-line1"),
    ADDRESS_LINE2("address-line2"),
    ADDRESS_LINE3("address-line3"),
    ADDRESS_LEVEL4("address-level4"),
    ADDRESS_LEVEL3("address-level3"),
    ADDRESS_LEVEL2("address-level2"),
    ADDRESS_LEVEL1("address-level1"),
    COUNTRY("country"),
    COUNTRY_NAME("country-name"),
    POSTAL_CODE("postal-code"),
    CC_NAME("cc-name"),
    CC_GIVEN_NAME("cc-given-name"),
    CC_ADDITIONAL_NAME("cc-additional-name"),
    CC_FAMILY_NAME("cc-family-name"),
    CC_NUMBER("cc-number"),
    CC_EXP("cc-exp"),
    CC_EXP_MONTH("cc-exp-month"),
    CC_EXP_YEAR("cc-exp-year"),
    CC_CSC("cc-csc"),
    CC_TYPE("cc-type"),
    TRANSACTION_CURRENCY("transaction-currency"),
    TRANSACTION_AMOUNT("transaction-amount"),
    LANGUAGE("language"),
    BDAY("bday"),
    BDAY_DAY("bday-day"),
    BDAY_MONTH("bday-month"),
    BDAY_YEAR("bday-year"),
    SEX("sex"),
    URL("url"),
    PHOTO("photo")
}

/**
 * Generic input element.
 *
 * @constructor
 * @param type the input type
 * @param className CSS class names
 * @param init an initializer extension function
 */
open class Input(
    type: InputType? = null,
    className: String? = null,
    init: (Input.() -> Unit)? = null
) : Widget(className) {

    /**
     * The input type.
     */
    var type by refreshOnUpdate(type)

    /**
     * Specifies a filter for what file types the user can pick from the file input dialog box (only for type="file").
     */
    var accept: String? by refreshOnUpdate()

    /**
     * Specifies an alternate text for images (only for type="image").
     */
    var alt: String? by refreshOnUpdate()

    /**
     * Specifies the auto complete mode of the <input> element.
     */
    var autocomplete: Autocomplete? by refreshOnUpdate()

    /**
     * Specifies that an <input> element should automatically get focus when the page loads.
     */
    var autofocus: Boolean? by refreshOnUpdate()

    /**
     * Specifies that an <input> element should be pre-selected when the page loads (for type="checkbox" or type="radio").
     */
    var checked: Boolean? by refreshOnUpdate()

    /**
     * Specifies that an <input> element should be disabled.
     */
    var disabled: Boolean? by refreshOnUpdate()

    /**
     * Specifies the form the <input> element belongs to.
     */
    var form: String? by refreshOnUpdate()

    /**
     * Specifies the URL of the file that will process the input control when the form is submitted (for type="submit" and type="image").
     */
    var formaction: String? by refreshOnUpdate()

    /**
     * Specifies how the form-data should be encoded when submitting it to the server (for type="submit" and type="image").
     */
    var formenctype: String? by refreshOnUpdate()

    /**
     * Defines the HTTP method for sending data to the action URL (for type="submit" and type="image").
     */
    var formmethod: String? by refreshOnUpdate()

    /**
     * Defines that form elements should not be validated when submitted.
     */
    var formnovalidate: Boolean? by refreshOnUpdate()

    /**
     * Specifies where to display the response that is received after submitting the form (for type="submit" and type="image".
     */
    var formtarget: String? by refreshOnUpdate()

    /**
     * Specifies the maximum value for an <input> element.
     */
    var max: String? by refreshOnUpdate()

    /**
     * Specifies the minimum number of characters required in an <input> element.
     */
    var maxlength: Int? by refreshOnUpdate()

    /**
     * Specifies a minimum value for an <input> element.
     */
    var min: String? by refreshOnUpdate()

    /**
     * Specifies the minimum number of characters required in an <input> element.
     */
    var minlength: Int? by refreshOnUpdate()

    /**
     * Specifies that a user can enter more than one value in an <input> element.
     */
    var multiple: Boolean? by refreshOnUpdate()

    /**
     * Specifies the name of an <input> element.
     */
    var name: String? by refreshOnUpdate()

    /**
     * Specifies a regular expression that an <input> element's value is checked against.
     */
    var pattern: String? by refreshOnUpdate()

    /**
     * Specifies a short hint that describes the expected value of an <input> element.
     */
    var placeholder: String? by refreshOnUpdate()

    /**
     * Specifies that an input field is read-only.
     */
    var readonly: Boolean? by refreshOnUpdate()

    /**
     * Specifies that an input field must be filled out before submitting the form.
     */
    var required: Boolean? by refreshOnUpdate()

    /**
     * Specifies the width, in characters, of an <input> element.
     */
    var size: Int? by refreshOnUpdate()

    /**
     * Specifies the URL of the image to use as a submit button (only for type="image").
     */
    var src: String? by refreshOnUpdate()

    /**
     * Specifies the interval between legal numbers in an input field.
     */
    var step: Double? by refreshOnUpdate()

    /**
     * Specifies the value of an <input> element.
     */
    var value: String? by refreshOnUpdate()

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun render(): VNode {
        return render("input")
    }

    override fun buildAttributeSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributeSet(attributeSetBuilder)
        type?.let {
            attributeSetBuilder.add("type", it.type)
        }
        accept?.let {
            attributeSetBuilder.add("accept", it)
        }
        alt?.let {
            attributeSetBuilder.add("alt", it)
        }
        autocomplete?.let {
            attributeSetBuilder.add("autocomplete", it.type)
        }
        autofocus?.let {
            if (it) attributeSetBuilder.add("autofocus")
        }
        checked?.let {
            if (it) attributeSetBuilder.add("checked")
        }
        disabled?.let {
            if (it) attributeSetBuilder.add("disabled")
        }
        form?.let {
            attributeSetBuilder.add("form", it)
        }
        formaction?.let {
            attributeSetBuilder.add("formaction", it)
        }
        formenctype?.let {
            attributeSetBuilder.add("formenctype", it)
        }
        formmethod?.let {
            attributeSetBuilder.add("formmethod", it)
        }
        formnovalidate?.let {
            if (it) attributeSetBuilder.add("formnovalidate")
        }
        formtarget?.let {
            attributeSetBuilder.add("formtarget", it)
        }
        max?.let {
            attributeSetBuilder.add("max", it)
        }
        maxlength?.let {
            attributeSetBuilder.add("maxlength", it.toString())
        }
        min?.let {
            attributeSetBuilder.add("min", it)
        }
        minlength?.let {
            attributeSetBuilder.add("minlength", it.toString())
        }
        multiple?.let {
            if (it) attributeSetBuilder.add("multiple")
        }
        name?.let {
            attributeSetBuilder.add("name", it)
        }
        pattern?.let {
            attributeSetBuilder.add("pattern", it)
        }
        placeholder?.let {
            attributeSetBuilder.add("placeholder", it)
        }
        readonly?.let {
            if (it) attributeSetBuilder.add("readonly")
        }
        required?.let {
            if (it) attributeSetBuilder.add("required")
        }
        size?.let {
            attributeSetBuilder.add("size", it.toString())
        }
        src?.let {
            attributeSetBuilder.add("src", it)
        }
        step?.let {
            attributeSetBuilder.add("step", it.toString())
        }
        value?.let {
            attributeSetBuilder.add("value", it)
        }
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.input(
    type: InputType? = null,
    className: String? = null,
    init: (Input.() -> Unit)? = null
): Input {
    val input = Input(type, className, init)
    this.add(input)
    return input
}
