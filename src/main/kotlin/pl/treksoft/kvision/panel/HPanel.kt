package pl.treksoft.kvision.panel

open class HPanel(
    justify: FLEXJUSTIFY? = null, alignItems: FLEXALIGNITEMS? = null, spacing: Int? = null,
    classes: Set<String> = setOf()
) : FlexPanel(
    null,
    null, justify, alignItems, null, spacing, classes
)
