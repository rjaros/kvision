package pl.treksoft.kvision.form.text

open class TextArea(cols: Int? = null, rows: Int? = null, value: String? = null,
                    label: String? = null, rich: Boolean = false) : AbstractText(label, rich) {

    var cols
        get() = input.cols
        set(value) {
            input.cols = value
        }
    var rows
        get() = input.rows
        set(value) {
            input.rows = value
        }
    var wrapHard
        get() = input.wrapHard
        set(value) {
            input.wrapHard = value
        }

    final override val input: TextAreaInput = TextAreaInput(cols, rows, value).apply { id = idc }

    init {
        @Suppress("LeakingThis")
        input.eventTarget = this
        this.addInternal(input)
        this.addInternal(validationInfo)
    }
}
