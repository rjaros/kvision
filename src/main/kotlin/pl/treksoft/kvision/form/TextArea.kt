package pl.treksoft.kvision.form

open class TextArea(cols: Int? = null, rows: Int? = null, placeholder: String? = null, value: String? = null,
                    name: String? = null, maxlength: Int? = null, label: String? = null, rich: Boolean = false,
                    disabled: Boolean = false) : AbstractText(label, rich) {

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

    final override val input: TextAreaInput = TextAreaInput(cols, rows, placeholder, value, name, maxlength,
            disabled, idc)

    init {
        this.addInternal(input)
    }
}
