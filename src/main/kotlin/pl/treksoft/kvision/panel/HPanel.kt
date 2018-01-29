package pl.treksoft.kvision.panel

open class HPanel(
    wrap: FLEXWRAP? = null, justify: FLEXJUSTIFY? = null, alignItems: FLEXALIGNITEMS? = null, spacing: Int? = null,
    classes: Set<String> = setOf()
) : FlexPanel(
    null,
    wrap, justify, alignItems, null, spacing, classes
)
