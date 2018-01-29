package test.pl.treksoft.kvision.data

import com.lightningkite.kotlin.observable.list.observableListOf
import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.data.DataComponent
import pl.treksoft.kvision.data.DataContainer
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class DataContainerSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")

            class Model(value: String) : DataComponent() {
                var value: String by obs(value)
            }

            val model = observableListOf(Model("First"), Model("Second"))
            val container = DataContainer(model, { index -> Label(model[index].value) })
            root.add(container)
            val element = document.getElementById("test")
            assertEquals(
                "<div style=\"display: flex; flex-direction: column;\"><div><span>First</span></div><div><span>Second</span></div></div>",
                element?.innerHTML,
                "Should render correct data container"
            )
            model.add(Model("Third"))
            assertEquals(
                "<div style=\"display: flex; flex-direction: column;\"><div><span>First</span></div><div><span>Second</span></div><div><span>Third</span></div></div>",
                element?.innerHTML,
                "Should render correct data container after model change"
            )
            model[1].value = "Changed"
            assertEquals(
                "<div style=\"display: flex; flex-direction: column;\"><div><span>First</span></div><div><span>Changed</span></div><div><span>Third</span></div></div>",
                element?.innerHTML,
                "Should render correct data container after model element change"
            )
        }
    }

}