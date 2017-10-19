package pl.treksoft.kvision

import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.*
import pl.treksoft.kvision.dropdown.DD.*
import pl.treksoft.kvision.dropdown.DropDown
import pl.treksoft.kvision.form.CHECKBOXSTYLE
import pl.treksoft.kvision.form.CheckBox
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.form.RADIOSTYLE
import pl.treksoft.kvision.form.Radio
import pl.treksoft.kvision.form.TEXTINPUTTYPE
import pl.treksoft.kvision.form.Text
import pl.treksoft.kvision.form.TextInput
import pl.treksoft.kvision.html.*
import pl.treksoft.kvision.html.TAG.DIV
import pl.treksoft.kvision.html.TAG.H1
import pl.treksoft.kvision.modal.Alert
import pl.treksoft.kvision.modal.Confirm
import pl.treksoft.kvision.modal.Modal
import pl.treksoft.kvision.panel.*
import pl.treksoft.kvision.routing.routing
import pl.treksoft.kvision.utils.perc
import pl.treksoft.kvision.utils.px

class Showcase : ApplicationBase() {

    override fun start(state: Map<String, Any>) {
        val root = Root("showcase")

        val container = Container(setOf("abc", "def"))
        val h1 = Tag(H1, "To jest <i>test pisania</i> tekstu", false, null, classes = setOf("test", "test2"))
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

        val textField = TextInput(placeholder = "Wprowadź hasło ...", value = "abc")
        root.add(textField)
        textField.setEventListener<TextInput> {
            input = { e ->
                println("i" + self.value)
            }
            change = { e ->
                println("c" + self.value)
            }
        }
        val passwordField = TextInput(TEXTINPUTTYPE.PASSWORD)
        root.add(passwordField)

        val textField2 = TextInput(placeholder = "Disabled")
        textField2.disabled = true
        textField2.size = INPUTSIZE.LARGE
        root.add(textField2)

        val checkbox = CheckBox(true, label = "Kliknij aby <b>przetestować</b>", rich = true, circled = true,
                style = CHECKBOXSTYLE.DANGER)
        root.add(checkbox)
        checkbox.setEventListener<CheckBox> {
            click = { e ->
                println("click" + self.value)
            }
            change = { e -> println("change" + self.value) }
        }

        val radio = Radio(true, name = "radios", label = "Opcja 1", inline = true,
                style = RADIOSTYLE.DANGER, extraValue = "o1")
        val radio2 = Radio(false, name = "radios", label = "Opcja 2", rich = true, inline = true,
                style = RADIOSTYLE.WARNING, extraValue = "o2")
        val radio3 = Radio(false, name = "radios", label = "Opcja 3", inline = true,
                style = RADIOSTYLE.PRIMARY, squared = true, extraValue = "o3")
        root.add(radio)
        root.add(radio2)
        root.add(radio3)
        radio.setEventListener<CheckBox> {
            click = { e ->
                println("rclick" + self.value)
            }
            change = { e -> println("rchange" + self.value) }
        }

        val text = Text(placeholder = "Pole formularza", maxlength = 5, label = "To jest pole")
        root.add(text)

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
//        tabs.addTab("Test zakładki3", tabs2, "fa-bars")

        val split = SplitPanel()
        split.add(tabs)

        val split2 = SplitPanel(DIRECTION.HORIZONTAL)
        val t1 = Tag(TAG.DIV, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor. Suspendisse vel fermentum purus, vel ornare tellus. Vivamus dictum, risus non viverra venenatis, magna mi pharetra purus, nec dignissim risus tortor a sem. Donec tincidunt dui ut eros laoreet consectetur. Nam dapibus vestibulum sem, eget accumsan ex vestibulum ac. Curabitur ac mi sit amet eros sodales dictum. Sed at felis at nunc aliquam finibus. Vestibulum lorem nulla, dictum ac libero non, mattis dictum nisl. Aenean semper lorem turpis. Praesent pellentesque ligula est, viverra molestie leo imperdiet ut. Nam vitae hendrerit justo. Nullam tincidunt et nibh ac volutpat. Aliquam vulputate mi aliquam fermentum rhoncus.\n" +
                "\n" +
                "Proin porttitor diam id massa eleifend aliquet. Morbi nec erat porttitor, placerat lorem et, dignissim lectus. Cras ultricies posuere arcu, et pharetra dui laoreet in. Sed nec ipsum in sapien vestibulum maximus eu id nunc. Ut finibus aliquam nisi id vehicula. Phasellus sodales lobortis orci, non interdum risus dignissim quis. Proin bibendum consectetur diam nec mattis. Suspendisse dictum vulputate metus at tincidunt.")
        t1.padding = 5.px()
        split2.add(t1)
        val t2 = Tag(TAG.DIV, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor. Suspendisse vel fermentum purus, vel ornare tellus. Vivamus dictum, risus non viverra venenatis, magna mi pharetra purus, nec dignissim risus tortor a sem. Donec tincidunt dui ut eros laoreet consectetur. Nam dapibus vestibulum sem, eget accumsan ex vestibulum ac. Curabitur ac mi sit amet eros sodales dictum. Sed at felis at nunc aliquam finibus. Vestibulum lorem nulla, dictum ac libero non, mattis dictum nisl. Aenean semper lorem turpis. Praesent pellentesque ligula est, viverra molestie leo imperdiet ut. Nam vitae hendrerit justo. Nullam tincidunt et nibh ac volutpat. Aliquam vulputate mi aliquam fermentum rhoncus.\n" +
                "\n" +
                "Proin porttitor diam id massa eleifend aliquet. Morbi nec erat porttitor, placerat lorem et, dignissim lectus. Cras ultricies posuere arcu, et pharetra dui laoreet in. Sed nec ipsum in sapien vestibulum maximus eu id nunc. Ut finibus aliquam nisi id vehicula. Phasellus sodales lobortis orci, non interdum risus dignissim quis. Proin bibendum consectetur diam nec mattis. Suspendisse dictum vulputate metus at tincidunt.")
        t2.padding = 10.px()
        split2.add(t2)
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
        p.border = Border(3.px(), BORDERSTYLE.SOLID)
        p.borderTop = Border(4.px(), BORDERSTYLE.DASHED, COLOR.RED)
        p.borderBottom = Border(4.px(), BORDERSTYLE.DASHED, 0x00ff00)
        p.borderLeft = Border(4.px(), BORDERSTYLE.SOLID, 0xff00ff)
        p.marginTop = 30.px()
        p.paddingRight = 30.px()
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
        img.opacity = 0.5
        root.add(img)

        val grid = ResponsiveGridPanel(align = ALIGN.RIGHT)
        grid.add(Tag(DIV, "0,0"), 0, 0)
        grid.add(Tag(DIV, "1,1"), 1, 1)
        grid.add(Tag(DIV, "2,2"), 2, 2)
        root.add(grid)

        val grid2 = ResponsiveGridPanel(align = ALIGN.CENTER)
        grid2.add(Tag(DIV, "0,0"), 0, 0, 8)
        grid2.add(Tag(DIV, "0,1"), 0, 1, 4)
        grid2.add(Tag(DIV, "1,1"), 1, 1, 8, 4)
        root.add(grid2)

        val flexPanel = FlexPanel(FLEXDIR.ROW)
        flexPanel.add(Label("1"), 3, 1)
        flexPanel.add(Label("2"), 2, 2)
        flexPanel.add(Label("3"), 1, 1)
        flexPanel.add(tabs2, 4, 1)
        root.add(flexPanel)

        val hPanel = HPanel(FLEXJUSTIFY.CENTER)
        hPanel.add(Label("h1"), basis = 10)
        hPanel.add(Label("h2"), basis = 20)
        hPanel.add(Label("h3"), basis = 10)
        root.add(hPanel)

        val vPanel = VPanel(alignItems = FLEXALIGNITEMS.CENTER)
        vPanel.add(Label("h1"), basis = 10)
        vPanel.add(Label("h2"), basis = 20)
        vPanel.add(Label("h3"), basis = 10)
        root.add(vPanel)

        val grid3 = GridPanel(templateColumns = "1fr 1fr 1fr")
        grid3.background = Background(0xCCCCCC, Img("kotlin.png"), 50.perc(), 50.perc(), size = BGSIZE.CONTAIN,
                repeat = BGREPEAT.NOREPEAT, attachment = BGATTACH.FIXED)
        grid3.add(Label("hh1"))
        grid3.add(Label("hh2"))
        grid3.add(Label("hh3"))
        grid3.add(Label("hh4"))
        root.add(grid3)

        val grid4 = GridPanel(justifyItems = GRIDJUSTIFY.CENTER)
        grid4.colorHex = 0x00ff00
        grid4.add(Label("hh1"), 1, 1)
        grid4.add(Label("hh2"), 2, 2)
        grid4.add(Label("hh3"), 3, 3)
        grid4.add(Label("hh4"), 4, 4)
        root.add(grid4)

        val dock = DockPanel()
        dock.colorName = COLOR.AQUA
        dock.add(Label("left<br/>left", rich = true), SIDE.LEFT)
        dock.add(Label("right"), SIDE.RIGHT)
        dock.add(Label("up"), SIDE.UP)
        dock.add(Label("down"), SIDE.DOWN)
        dock.add(Label("center"), SIDE.CENTER)
        dock.border = Border(7.px(), BORDERSTYLE.INSET, COLOR.DARKVIOLET)
//        root.add(dock)

        val pa = HPanel(alignItems = FLEXALIGNITEMS.FLEXEND)
        pa.add(Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce nec fringilla turpis, vel molestie dolor. Vestibulum ut ex eget orci porta gravida eu sit amet tortor. Suspendisse vel fermentum purus, vel ornare tellus. Vivamus dictum, risus non viverra venenatis, magna mi pharetra purus, nec dignissim risus tortor a sem. Donec tincidunt dui ut eros laoreet consectetur. Nam dapibus vestibulum sem, eget accumsan ex vestibulum ac. Curabitur ac mi sit amet eros sodales dictum. Sed at felis at nunc aliquam finibus. Vestibulum lorem nulla, dictum ac libero non, mattis dictum nisl. Aenean semper lorem turpis. Praesent pellentesque ligula est, viverra molestie leo imperdiet ut. Nam vitae hendrerit justo. Nullam tincidunt et nibh ac volutpat. Aliquam vulputate mi aliquam fermentum rhoncus."), 3)
        pa.add(Image(Img("kotlin.png")), 1)
        pa.add(dock, 2, alignSelf = FLEXALIGNITEMS.FLEXSTART)
        dock.width = 400.px()
        root.add(pa)

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
                println(textField.value)
                println(checkbox.value)
                textField2.disabled = false
                grid4.colorHex = 0xff0000
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
