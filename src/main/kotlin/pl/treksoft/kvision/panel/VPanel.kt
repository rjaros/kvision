package pl.treksoft.kvision.panel

open class VPanel(
    justify: FLEXJUSTIFY? = null, alignItems: FLEXALIGNITEMS? = null,
    classes: Set<String> = setOf()
) : FlexPanel(
    FLEXDIR.COLUMN,
    null, justify, alignItems, null, classes
)
