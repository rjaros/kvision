package pl.treksoft.kvision

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.Img
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.dropdown.DD.*
import pl.treksoft.kvision.dropdown.DropDown
import pl.treksoft.kvision.html.*
import pl.treksoft.kvision.html.TAG.DIV
import pl.treksoft.kvision.html.TAG.H1
import pl.treksoft.kvision.modal.Alert
import pl.treksoft.kvision.modal.Confirm
import pl.treksoft.kvision.modal.Modal
import pl.treksoft.kvision.panel.DIRECTION
import pl.treksoft.kvision.panel.GRIDTYPE
import pl.treksoft.kvision.panel.GridPanel
import pl.treksoft.kvision.panel.HPanel
import pl.treksoft.kvision.panel.SplitPanel
import pl.treksoft.kvision.panel.TabPanel
import pl.treksoft.kvision.panel.VPanel
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
        dd.setEventListener<DropDown> {
            showBsDropdown = { e -> println("show" + (e.detail)) }
            shownBsDropdown = { e -> println("shown" + e.detail) }
            hideBsDropdown = { e ->
                println("hide" + e.detail)
                e.detail.preventDefault()
            }
            hiddenBsDropdown = { e -> println("hidden" + e.detail) }
        }

        val dd2 = DropDown("Dropdown2", listOf("abc" to "#!/abc", "def" to "#!/def", "xyz" to DISABLED.type,
                "Header" to HEADER.type, "Separtatorek" to SEPARATOR.type
        ), "flag", dropup = true)
        root.add(dd2)
        dd2.setEventListener<DropDown> {
            hideBsDropdown = { e -> println("hide" + e.detail) }
            hiddenBsDropdown = { e -> println("hidden" + e.detail) }
        }

        val dd3 = DropDown("Dropdown3", icon = "file")
        dd3.add(Tag(TAG.H4, "ABC"))
        dd3.add(Button("To jest button"))
        dd3.add(Image(Img("kotlin.png")))
        root.add(dd3)

        val tabs2 = TabPanel()
        tabs2.addTab("XXX", Label("XXX"), "fa-flag")
        tabs2.addTab("YYY", Label("YYY"), "fa-flag")

        val tabs = TabPanel()
        tabs.addTab("Test zakładki", Label("test zakładki"), "fa-flag")
        tabs.addTab("Test zakładki2", Label("test zakładki2"))
        tabs.addTab("Test zakładki3", tabs2, "fa-bars")

        val split = SplitPanel()
        split.add(tabs)

        val split2 = SplitPanel(DIRECTION.HORIZONTAL)
        split2.add(Tag(TAG.DIV, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor. Suspendisse vel fermentum purus, vel ornare tellus. Vivamus dictum, risus non viverra venenatis, magna mi pharetra purus, nec dignissim risus tortor a sem. Donec tincidunt dui ut eros laoreet consectetur. Nam dapibus vestibulum sem, eget accumsan ex vestibulum ac. Curabitur ac mi sit amet eros sodales dictum. Sed at felis at nunc aliquam finibus. Vestibulum lorem nulla, dictum ac libero non, mattis dictum nisl. Aenean semper lorem turpis. Praesent pellentesque ligula est, viverra molestie leo imperdiet ut. Nam vitae hendrerit justo. Nullam tincidunt et nibh ac volutpat. Aliquam vulputate mi aliquam fermentum rhoncus.\n" +
                "\n" +
                "Proin porttitor diam id massa eleifend aliquet. Morbi nec erat porttitor, placerat lorem et, dignissim lectus. Cras ultricies posuere arcu, et pharetra dui laoreet in. Sed nec ipsum in sapien vestibulum maximus eu id nunc. Ut finibus aliquam nisi id vehicula. Phasellus sodales lobortis orci, non interdum risus dignissim quis. Proin bibendum consectetur diam nec mattis. Suspendisse dictum vulputate metus at tincidunt."))
        split2.add(Tag(TAG.DIV, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor. Suspendisse vel fermentum purus, vel ornare tellus. Vivamus dictum, risus non viverra venenatis, magna mi pharetra purus, nec dignissim risus tortor a sem. Donec tincidunt dui ut eros laoreet consectetur. Nam dapibus vestibulum sem, eget accumsan ex vestibulum ac. Curabitur ac mi sit amet eros sodales dictum. Sed at felis at nunc aliquam finibus. Vestibulum lorem nulla, dictum ac libero non, mattis dictum nisl. Aenean semper lorem turpis. Praesent pellentesque ligula est, viverra molestie leo imperdiet ut. Nam vitae hendrerit justo. Nullam tincidunt et nibh ac volutpat. Aliquam vulputate mi aliquam fermentum rhoncus.\n" +
                "\n" +
                "Proin porttitor diam id massa eleifend aliquet. Morbi nec erat porttitor, placerat lorem et, dignissim lectus. Cras ultricies posuere arcu, et pharetra dui laoreet in. Sed nec ipsum in sapien vestibulum maximus eu id nunc. Ut finibus aliquam nisi id vehicula. Phasellus sodales lobortis orci, non interdum risus dignissim quis. Proin bibendum consectetur diam nec mattis. Suspendisse dictum vulputate metus at tincidunt."))
        split.add(split2)
        root.add(split)
        split.setEventListener<SplitPanel> {
            dragSplitPanel = { e ->
                if ((e.detail.asDynamic()).newWidth > 400) {
                    e.detail.preventDefault()
                }
            }
        }

        val p = Tag(TAG.P, "To jest prawo", align = ALIGN.RIGHT)
        p.title = "Tytuł"
        root.add(p)
        val del = Tag(TAG.DEL, "To jest deleted")
        root.add(del)

        val list = ListTag(LIST.DL_HORIZ, listOf("abc", "de<b>fdasdasdasddasd</b>tdasdas", "Dasdsada",
                "dasdasdads"), true)
        root.add(list)

        val list2 = ListTag(LIST.OL, null)
        list2.add(Tag(TAG.H4, "ABC"))
        list2.add(Button("To jest button"))
        list2.add(Image(Img("kotlin.png")))
        root.add(list2)

        val img = Image(Img("kotlin.png"), "Image", true, IMAGESHAPE.ROUNDED)
        root.add(img)

        val grid = GridPanel(gridtype = GRIDTYPE.DSG, align = ALIGN.RIGHT)
        grid.add(Tag(DIV, "0,0"), 0, 0)
        grid.add(Tag(DIV, "1,1"), 1, 1)
        grid.add(Tag(DIV, "2,2"), 2, 2)
        grid.add(Tag(DIV, "3,3"), 3, 3)
        root.add(grid)

        val grid2 = GridPanel(align = ALIGN.CENTER)
        grid2.add(Tag(DIV, "0,0"), 0, 0, 8)
        grid2.add(Tag(DIV, "0,1"), 0, 1, 4)
        grid2.add(Tag(DIV, "1,1"), 1, 1, 8, 4)
        root.add(grid2)

        val hPanel = HPanel(align = ALIGN.RIGHT)
        hPanel.add(Label("1"))
        hPanel.add(Label("2"))
        hPanel.add(Label("3"))
        root.add(hPanel)

        val vPanel = VPanel(align = ALIGN.CENTER)
        vPanel.add(Label("1"))
        vPanel.add(Label("2"))
        vPanel.add(Label("3"))
        root.add(vPanel)

        val modal = Modal("Test okienka")
        modal.add(Tag(TAG.H4, "ABC"))
        modal.add(Image(Img("kotlin.png")))
        modal.addButton(Button("To jest button"))
/*        modal.setEventListener<Modal> {
            hideBsModal = { e -> e.detail.preventDefault() }
        }*/
        val button = Button("To jest przycisk FA", "fa-flag", BUTTONSTYLE.DANGER)
        button.setEventListener<Button> {
            click = { _ ->
                println(self.text)
                dd3.text = "Zmiana"
                dd3.style = BUTTONSTYLE.WARNING
                dd3.disabled = true
/*                modal.show()
                window.setTimeout({
                    modal.size = MODALSIZE.SMALL
                    modal.animation = false
                }, 2000)*/
                if (split.visible) {
                    split.hide()
                } else {
                    split.show()
                }
            }
        }
        root.add(button)

        fun alerts() {
            Alert.show("This is alert", "A text of the <b>alert</b>", true) {
                println("abc")
                Alert.show("This is alert 2", "A text of the <b>alert</b> 2", true) {
                    println("def")
                }
            }
        }

        val button2 = Button("To jest przycisk", "flag", BUTTONSTYLE.DANGER)
        button2.setEventListener {
            click = { e ->
                dd.hide()
                println("2" + e)
                button.setEventListener {
                    click = null
                }
                alerts()
            }
        }
        root.add(button2)
        val button3 = Button("To jest przycisk IMG", image = Img("kotlin.png"))
        button3.setEventListener {
            click = { e ->
                Confirm.show("Pytanie", "Czy na pewno chcesz kliknąć przycisk?", noCallback = { println("no") }) {
                    dd.show()
                    println("3" + e)
                    button.setEventListener<Button> {
                        click = { _ -> println(self.text) }
                        dblclick = { e -> println("111" + e) }
                    }
                }
            }
        }
        root.add(button3)

        println("init routing")
        routing.on({ _ -> println("root") })
                .on("/abc", { _ -> println("abc") })
                .on("/test", { _ -> println("test") })
                .resolve()
//        jQuery(document).off(".data-api")

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
        return mapOf()
    }
}
