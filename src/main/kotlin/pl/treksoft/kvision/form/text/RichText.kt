package pl.treksoft.kvision.form.text

open class RichText(value: String? = null,
                    label: String? = null, rich: Boolean = false) : AbstractText(label, rich) {

    var inputHeight
        get() = input.height
        set(value) {
            input.height = value
        }

    final override val input: RichTextInput = RichTextInput(value).apply { id = idc }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(input)
    }
}
