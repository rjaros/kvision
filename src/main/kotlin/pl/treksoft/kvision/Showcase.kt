package pl.treksoft.kvision

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Img
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.dropdown.DropDown
import pl.treksoft.kvision.html.*
import pl.treksoft.kvision.html.TAG.H1
import pl.treksoft.kvision.routing.routing

class Showcase : ApplicationBase() {

    override fun start(state: Map<String, Any>) {
        val root = Root("showcase")
        val container = Container(setOf("abc", "def"))
        val h1 = Tag(H1, "To jest <i>test pisania</i> tekstu", false, ALIGN.NONE, classes = setOf("test", "test2"))
        container.add(h1)
        val label = Label("KVLabel1")
        container.add(label)
        val label2 = Label("KVLabel2")
        container.add(label2)
        root.add(container)
        label.hide()
        label.show()

        val link = Link("test", "http://www.google.pl")
        link.add(Tag(TAG.P, "Cośtam"))
        root.add(link)

        val dd = DropDown("Dropdown", listOf("abc" to "#!/x", "def" to "#!/y"), "flag")
        root.add(dd)

        val dd2 = DropDown("Dropdown2", listOf("abc" to "#!/abc", "def" to "#!/def"), "flag")
        root.add(dd2)

        val p = Tag(TAG.P, "To jest prawo", align = ALIGN.RIGHT)
        p.title = "Tytuł"
        root.add(p)
        val del = Tag(TAG.DEL, "To jest deleted")
        root.add(del)

        val list = ListTag(LIST.DL_HORIZ, listOf("abc", "de<b>fdasdasdasddasd</b>tdasdas", "Dasdsada", "dasdasdads"), true)
        root.add(list)

        val list2 = ListTag(LIST.OL, null)
        list2.add(Tag(TAG.H4, "ABC"))
        list2.add(Button("To jest button"))
        list2.add(Image(Img("kotlin.png")))
        root.add(list2)

        val img = Image(Img("kotlin.png"), "Image", true, IMAGE_SHAPE.ROUNDED)
        root.add(img)

        val button = Button("To jest przycisk FA", "fa-flag", BUTTON_STYLE.DANGER)
        button.setEventListener<Button> {
            click = { _ -> println(self.text) }
        }
        root.add(button)
        val button2 = Button("To jest przycisk", "flag", BUTTON_STYLE.DANGER)
        button2.setEventListener {
            click = { e ->
                println("2" + e)
                button.setEventListener {
                    click = null
                }
            }
        }
        root.add(button2)
        val button3 = Button("To jest przycisk IMG", image = Img("kotlin.png"))
        button3.setEventListener {
            click = { e ->
                println("3" + e)
                button.setEventListener<Button> {
                    click = { _ -> println(self.text) }
                    dblclick = { e -> println("111" + e) }
                }
            }
        }
        root.add(button3)

        println("init routing")
        routing.on({ -> println("root") })
                .on("/abc", { -> println("abc") })
                .on("/test", { -> println("test") })
                .resolve()

//        routing.on(RegExp("/abc/def/(.*)/(.*)/(.*)"), { x,y,z,u,v -> println(x) })

//        router.on("/test", { -> println("test") })

/*        val vnode = h("div", snOpt {
            on = snEvents {
                click = { e -> println(e) }
            }
        }, arrayOf(
                h("span", snOpt {
                    style = snStyle("fontWeight" to "bold", "fontStyle" to "italic")
                }, arrayOf("This is bold")),
                " and this is just normal text ",
                h("a", snOpt {
                    props = snProps("href" to "/foo", "target" to "_blank")
                }, "I\'ll take you places!")
        ))
        val v = patch(container, vnode)
        val vnode2 = virtualize("<a href='/top' target='_top'>Test2</a>")
        patch(v, vnode2)*/
    }

    override fun dispose(): Map<String, Any> {
        KVManager.shutdown()
        return mapOf<String, Any>()
    }
}