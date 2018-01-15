package pl.treksoft.kvision.form.spinner

import com.github.snabbdom.VNode
import pl.treksoft.jquery.JQuery
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair
import pl.treksoft.kvision.snabbdom.obj

enum class BUTTONSTYPE {
    NONE,
    HORIZONTAL,
    VERTICAL
}

enum class FORCETYPE(val value: String) {
    NONE("none"),
    ROUND("round"),
    FLOOR("floor"),
    CEIL("cail")
}

const val DEFAULT_STEP = 1.0
const val DEFAULT_MAX = 100

@Suppress("TooManyFunctions")
open class SpinnerInput(value: Number? = null, min: Int = 0, max: Int = DEFAULT_MAX, step: Double = DEFAULT_STEP,
                        decimals: Int = 0, buttonsType: BUTTONSTYPE = BUTTONSTYPE.VERTICAL,
                        forceType: FORCETYPE = FORCETYPE.NONE,
                        classes: Set<String> = setOf()) : Widget(classes + "form-control") {

    init {
        this.addSurroundingCssClass("input-group")
        if (buttonsType == BUTTONSTYPE.NONE) {
            this.addSurroundingCssClass("kv-spinner-btn-none")
        } else {
            this.removeSurroundingCssClass("kv-spinner-btn-none")
        }
        if (buttonsType == BUTTONSTYPE.VERTICAL) {
            this.addSurroundingCssClass("kv-spinner-btn-vertical")
        } else {
            this.removeSurroundingCssClass("kv-spinner-btn-vertical")
        }
        this.surroundingSpan = true
        this.refreshSpinner()
        this.setInternalEventListener<SpinnerInput> {
            change = {
                self.changeValue()
            }
        }
    }

    var value: Number? = value
        set(value) {
            field = value
            refreshState()
        }
    var startValue: Number? = value
        set(value) {
            field = value
            this.value = value
            refresh()
        }
    var min: Int = min
        set(value) {
            field = value
            refreshSpinner()
        }
    var max: Int = max
        set(value) {
            field = value
            refreshSpinner()
        }
    var step: Double = step
        set(value) {
            field = value
            refreshSpinner()
        }
    var decimals: Int = decimals
        set(value) {
            field = value
            refreshSpinner()
        }
    var buttonsType: BUTTONSTYPE = buttonsType
        set(value) {
            field = value
            refreshSpinner()
        }
    var forceType: FORCETYPE = forceType
        set(value) {
            field = value
            refreshSpinner()
        }
    var placeholder: String? = null
        set(value) {
            field = value
            refresh()
        }
    var name: String? = null
        set(value) {
            field = value
            refresh()
        }
    var disabled: Boolean = false
        set(value) {
            field = value
            refresh()
        }
    var autofocus: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    var readonly: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    var size: INPUTSIZE? = null
        set(value) {
            field = value
            refresh()
        }

    private var siblings: JQuery? = null

    override fun render(): VNode {
        return kvh("input")
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("type" to "text")
        startValue?.let {
            sn.add("value" to it.toString())
        }
        placeholder?.let {
            sn.add("placeholder" to it)
        }
        name?.let {
            sn.add("name" to it)
        }
        autofocus?.let {
            if (it) {
                sn.add("autofocus" to "autofocus")
            }
        }
        readonly?.let {
            if (it) {
                sn.add("readonly" to "readonly")
            }
        }
        if (disabled) {
            sn.add("disabled" to "true")
            value?.let {
                sn.add("value" to it.toString())
            }
        }
        return sn
    }

    protected open fun changeValue() {
        val v = getElementJQuery()?.`val`() as String?
        if (v != null && v.isNotEmpty()) {
            this.value = v.toDoubleOrNull()
        } else {
            this.value = null
        }
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun afterInsert(node: VNode) {
        getElementJQueryD()?.TouchSpin(getSettingsObj())
        siblings = getElementJQuery()?.parent(".bootstrap-touchspin")?.children("span")
        size?.let {
            siblings?.find("button")?.addClass(it.className)
        }
        this.getElementJQuery()?.on("change", { e, _ ->
            if (e.asDynamic().isTrigger != null) {
                val event = org.w3c.dom.events.Event("change")
                this.getElement()?.dispatchEvent(event)
            }
        })
        this.getElementJQuery()?.on("touchspin.on.min", { e, _ ->
            this.dispatchEvent("onMinBsSpinner", obj { detail = e })
        })
        this.getElementJQuery()?.on("touchspin.on.max", { e, _ ->
            this.dispatchEvent("onMaxBsSpinner", obj { detail = e })
        })
        refreshState()
    }

    override fun afterDestroy() {
        siblings?.remove()
        siblings = null
    }

    fun getValueAsString(): String? {
        return value?.toString()
    }

    fun spinUp(): SpinnerInput {
        getElementJQueryD()?.trigger("touchspin.uponce")
        return this
    }

    fun spinDown(): SpinnerInput {
        getElementJQueryD()?.trigger("touchspin.downonce")
        return this
    }

    private fun refreshState() {
        value?.let {
            getElementJQuery()?.`val`(it.toString())
        } ?: getElementJQueryD()?.`val`(null)
    }

    private fun refreshSpinner() {
        getElementJQueryD()?.trigger("touchspin.updatesettings", getSettingsObj())
    }

    private fun getSettingsObj(): dynamic {
        val verticalbuttons = buttonsType == BUTTONSTYPE.VERTICAL || buttonsType == BUTTONSTYPE.NONE
        return obj {
            this.min = min
            this.max = max
            this.step = step
            this.decimals = decimals
            this.verticalbuttons = verticalbuttons
            this.forcestepdivisibility = forceType.value
        }
    }
}
