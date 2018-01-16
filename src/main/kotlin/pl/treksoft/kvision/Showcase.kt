package pl.treksoft.kvision

import com.lightningkite.kotlin.observable.list.observableListOf
import pl.treksoft.kvision.basic.Label
import pl.treksoft.kvision.core.*
import pl.treksoft.kvision.data.DataComponent
import pl.treksoft.kvision.data.DataContainer
import pl.treksoft.kvision.dropdown.DD.*
import pl.treksoft.kvision.dropdown.DropDown
import pl.treksoft.kvision.form.Form
import pl.treksoft.kvision.form.FormPanel
import pl.treksoft.kvision.form.INPUTSIZE
import pl.treksoft.kvision.form.bool
import pl.treksoft.kvision.form.check.CheckBox
import pl.treksoft.kvision.form.check.Radio
import pl.treksoft.kvision.form.check.RadioGroup
import pl.treksoft.kvision.form.date
import pl.treksoft.kvision.form.select.AjaxOptions
import pl.treksoft.kvision.form.select.SELECTWIDTHTYPE
import pl.treksoft.kvision.form.select.Select
import pl.treksoft.kvision.form.select.SelectInput
import pl.treksoft.kvision.form.select.SelectOptGroup
import pl.treksoft.kvision.form.select.SelectOption
import pl.treksoft.kvision.form.spinner.FORCETYPE
import pl.treksoft.kvision.form.spinner.Spinner
import pl.treksoft.kvision.form.spinner.SpinnerInput
import pl.treksoft.kvision.form.string
import pl.treksoft.kvision.form.text.Password
import pl.treksoft.kvision.form.text.RichText
import pl.treksoft.kvision.form.text.TEXTINPUTTYPE
import pl.treksoft.kvision.form.text.Text
import pl.treksoft.kvision.form.text.TextArea
import pl.treksoft.kvision.form.text.TextAreaInput
import pl.treksoft.kvision.form.text.TextInput
import pl.treksoft.kvision.form.time.DateTime
import pl.treksoft.kvision.form.time.DateTimeInput
import pl.treksoft.kvision.html.*
import pl.treksoft.kvision.html.TAG.DIV
import pl.treksoft.kvision.html.TAG.H1
import pl.treksoft.kvision.modal.Alert
import pl.treksoft.kvision.modal.Confirm
import pl.treksoft.kvision.modal.Modal
import pl.treksoft.kvision.panel.*
import pl.treksoft.kvision.routing.routing
import pl.treksoft.kvision.snabbdom.obj
import pl.treksoft.kvision.utils.perc
import pl.treksoft.kvision.utils.px
import pl.treksoft.kvision.utils.toDateF
import kotlin.js.Date

class Showcase : ApplicationBase() {

    override fun start(state: Map<String, Any>) {
        data class DataForm(val a: String?, val b: Boolean?, val c: Date?)

        val f = DataForm("ala", true, Date())
        val form = Form {
            DataForm(it.string("a"), it.bool("b"), it.date("c"))
        }
        form.add("a", Text())
        form.add("b", CheckBox())
        form.add("c", DateTime())
        form.setData(f)
        val ret = form.getData()
        console.log(ret)

        class DataFormMap(map: Map<String, Any?>) {
            val name: String by map
            val age: Date     by map
        }

        val fm = DataFormMap(mapOf("name" to "Ala", "age" to Date()))
        val formm = Form {
            DataFormMap(it)
        }
        formm.add("name", Text())
        formm.add("age", DateTime())
        formm.setData(fm)
        val retm = formm.getData()
        console.log(retm.name)
        console.log(retm.age)

        val root = Root("showcase")

        class Model(p: Boolean, t: String) : DataComponent() {
            var p: Boolean by obs(p)
            var t: String by obs(t)
        }

        val model = observableListOf(Model(true, "Pierwszy"), Model(false, "Drugi"), Model(false, "Trzeci"))
        val datac = DataContainer(model, { element, index ->
            CheckBox(value = element.p,
                    label = if (element.p) "<b>" + (index + 1) + " " + element.t + "</b>" else element.t,
                    rich = true).setEventListener<CheckBox>({
                click = {
                    element.p = self.value
                }
            })
        })
        root.add(datac)

        val mbutton = Button("Pokaż wartości").setEventListener<Button> {
            click = {
                println(model.collection)
            }
            dblclick = {
                model.add(Model(true, "XXX"))
            }
        }
        root.add(mbutton)
        val mbutton2 = Button("Zaznacz").setEventListener<Button> {
            click = {
                model.forEach { it.p = true }
            }
            dblclick = {
                model.forEach { it.p = false }
            }
        }
        root.add(mbutton2)
        val textField = TextInput(value = "abc").apply { placeholder = "Wprowadź hasło ..." }
        val mbutton3 = Button("Ukryj/Pokaż").setEventListener<Button> {
            click = {
                if (datac.visible) datac.hide() else datac.show()
                if (textField.visible) textField.hide() else textField.show()
            }
        }
        root.add(mbutton3)

        val select = SelectInput(listOf("klucz1" to "Klucz 1", "klucz2" to "Klucz 2"), "klucz2,klucz1", multiple = true)
        root.add(select)

        val mbuttons = Button("Select").setEventListener<Button> {
            click = {
                println(select.value)
            }
        }
        root.add(mbuttons)

        val select2 = SelectInput(value = "klucz1")
        select2.add(SelectOption("klucz0", "Klucz 0", "Subtext 0", "flag"))
        select2.add(SelectOption(divider = true))
        select2.add(SelectOption("klucz1", "Klucz 1", "Subtext 1", "fa-flag"))
        select2.add(SelectOption("klucz2", "Klucz 2", disabled = true))
        root.add(select2)

        val select3 = SelectInput().apply {
            placeholder = "Wybierz opcje"
            emptyOption = true
            liveSearch = true
            style = BUTTONSTYLE.WARNING
            selectWidthType = SELECTWIDTHTYPE.FIT
        }
        select3.add(SelectOptGroup("Opcje pierwsze", listOf("k" to "Opcja pierwsza 1", "m" to "Opcja pierwsza 2")))
        val sopt = SelectOptGroup("Opcje drugie", maxOptions = 2)
        sopt.add(SelectOption("a", "Opcja druga 1", "Subtext 1"))
        sopt.add(SelectOption("b", "Opcja druga 2"))
        sopt.add(SelectOption("c", "Opcja druga 3").apply { color = Color(COLOR.RED) })
        select3.add(sopt)
        root.add(select3)

        val mbuttons3 = Button("Select").setEventListener<Button> {
            click = {
                println(select3.value)
                select3.toggleOptions()
            }
        }
        root.add(mbuttons3)

        val rg = RadioGroup(listOf("o1" to "Pierwsza opcja", "o2" to "Druga opcja"), label = "Radio buttony")
        root.add(rg)
        val rgbutton = Button("Sprawdź radio").setEventListener<Button> {
            click = {
                println(rg.value)
            }
        }
        root.add(rgbutton)
        val rg2button = Button("Ustaw radio").setEventListener<Button> {
            click = {
                rg.value = "o2"
            }
            dblclick = {
                rg.value = null
            }
        }
        root.add(rg2button)

        val select5 = Select(listOf("a" to "Pierwsza", "b" to "Druga"), "a", label = "Lista wyboru")
        root.add(select5)

        val text = Text(label = "To jest pole").apply {
            placeholder = "Pole formularza"
            maxlength = 5
        }

        val select6 = Select(label = "Lista wyboru 2", value = "b")
        select6.add(SelectOption("a", "Opcja 1"))
        select6.add(SelectOption("b", "Opcja 2"))
        select6.add(SelectOption("c", "Opcja 3"))
        select6.setEventListener<Select> {
            showBsSelect = { e ->
                println("show")
            }
            shownBsSelect = { e ->
                println("shown")
            }
            hideBsSelect = { e ->
                println("hide")
                e.detail.preventDefault()
            }
            hiddenBsSelect = { e ->
                println("hidden")
            }
            renderedBsSelect = { e ->
                println("rendered")
            }
            refreshedBsSelect = { e ->
                println("refreshed")
            }
            loadedBsSelect = { e ->
                println("loaded")
            }
            changedBsSelect = { e ->
                println(e.detail.clickedIndex)
                if (e.detail.clickedIndex == 0) {
                    self.options = listOf("x" to "x", "y" to "y", "z" to "z")
                    self.value = "y"
                    text.value = "ole"
                    textField.value = "ole2"
                } else {
                    self.add(SelectOption("x", "XXX"))
                }
            }
        }
        root.add(select6)

        val select7 = SelectInput().apply {
            ajaxOptions = AjaxOptions("https://api.github.com/search/repositories", processData = {
                it.items.map { item ->
                    obj {
                        this.value = item.id
                        this.text = item.name
                        this.data = obj {
                            this.subtext = item.owner.login
                        }
                    }
                }
            }, processParams = obj {
                q = "{{{q}}}"
            })
        }
        root.add(select7)

        val select8 = Select(label = "Wybierz repozytorium").apply {
            emptyOption = true
            ajaxOptions = AjaxOptions("https://api.github.com/search/repositories", processData = {
                it.items.map { item ->
                    obj {
                        this.value = item.id
                        this.text = item.name
                        this.data = obj {
                            this.subtext = item.owner.login
                        }
                    }
                }
            }, processParams = obj {
                q = "{{{q}}}"
            }, minLength = 3, requestDelay = 1000)
            setEventListener<Select> {
                change = { _ ->
                    println(self.value)
                }
            }
        }
        root.add(select8)
        val mbuttons8 = Button("Sprawdz repozytorium").setEventListener<Button> {
            click = {
                println(select8.value)
                select8.value = null
            }
        }
        root.add(mbuttons8)

        val htmlArea = RichText("test<b>Boldzik</b>", "Pole html").apply {
            size = INPUTSIZE.SMALL
            placeholder = "Wprowadź rich text"
            width = 50.perc()
            inputHeight = 200.px()
        }
        root.add(htmlArea)
        htmlArea.setEventListener<RichText> {
            change = {
                console.log(self.value)
            }
        }
        val mbuttons9 = Button("Sprawdz html").setEventListener<Button> {
            click = {
                println(htmlArea.value)
            }
        }
        root.add(mbuttons9)

        val date = DateTimeInput().apply {
            placeholder = "Wprowadź datę"
            size = INPUTSIZE.LARGE
            todayBtn = true
            showMeridian = true
            daysOfWeekDisabled = arrayOf(0, 6)

        }
        root.add(date)

        val date2 = DateTimeInput(Date(), format = "DD-MM-YY").apply {
            disabled = true
        }
        root.add(date2)

        val date3 = DateTimeInput(Date(), format = "DD-MM-YYYY hh:mm A").apply {
            readonly = true
            showMeridian = true
        }
        root.add(date3)

        val date4 = DateTime(Date(), format = "HH:mm", label = "Wprowadź datę wpływu")
        date4.setEventListener<DateTime> {
            showBsDateTime = {
                println("show dt")
            }
            hideBsDateTime = {
                println("hide dt")
            }
        }
        root.add(date4)

        val mbuttons10 = Button("Sprawdz daty").setEventListener<Button> {
            click = {
                println(date.value)
                println(date.getValueAsString())
                println(date2.value)
                println(date2.getValueAsString())
                println(date3.value)
                println(date3.getValueAsString())
                println(date4.value)
                println(date4.getValueAsString())
                date.value = "2017-01-16".toDateF("YYYY-MM-DD")
                date.showPopup()
                date.weekStart = 1
                date4.value = null
                date4.format = "mm:HH"
                date4.disabled = !date4.disabled
            }
        }
        root.add(mbuttons10)

        val container = SimplePanel(setOf("abc", "def"))
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

        val textField2 = TextInput().apply { placeholder = "Disabled" }
        textField2.disabled = true
        textField2.size = INPUTSIZE.LARGE
        root.add(textField2)

        val checkbox = CheckBox(true, label = "Kliknij aby <b>przetestować</b>", rich = true)
        root.add(checkbox)
        checkbox.setEventListener<CheckBox> {
            click = { e ->
                println("click" + self.value)
            }
            change = { e -> println("change" + self.value) }
        }

/*        val radio = Radio(true, name = "radios", label = "Opcja 1", inline = true,
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
        }*/
        root.add(text)

        val textareainput = TextAreaInput(cols = 5, rows = 2, value = "To jest tekst\nTo jest <b>te</b></textarea>kst2").apply {
            placeholder = "..."
        }
        root.add(textareainput)

        val textarea = TextArea(cols = 5, rows = 2, value = "To jest tekst\nTo jest <b>te</b></textarea>kst2", label = "Pole długie").apply {
            placeholder = "..."
        }
        root.add(textarea)
        textarea.setEventListener<TextArea> {
            input = { e ->
                println("ta i" + self.value)
            }
            change = { e ->
                println("ta c" + self.value)
            }
        }

        class Formularz(val map: Map<String, Any?>) {
            val text: String? by map
            val password: String? by map
            val textarea: String? by map
            val richtext: String? by map
            val data: Date? by map
            val checkbox: Boolean by map
            val radio: Boolean by map
            val select: String? by map
            val spinner: Double? by map
            val radiogroup: String? by map
        }

        val formPanel = FormPanel {
            Formularz(it)
        }.apply {
            add("text", Text(label = "Tekst"), required = true, validatorMessage = { "Wprowadź tylko cyfry" }) {
                it.getValue()?.matches("^[0-9]+$")
            }
            add("password", Password(label = "Hasło"), required = true,
                    validatorMessage = { "Wprowadź co najmniej 5 znaków" }) {
                (it.getValue()?.length ?: 0) >= 5
            }
            add("textarea", TextArea(label = "Obszar"), required = true)
            add("richtext", RichText(label = "Obszar WYSIWYG"), required = true)
            add("data", DateTime(format = "YYYY-MM-DD", label = "Data"), required = true)
            add("checkbox", CheckBox(label = "Checkbox")) { it.getValue() }
            add("radio", Radio(label = "Radiobutton")) { it.getValue() }
            add("select", Select(options = listOf("a" to "Pierwsza opcja", "b" to "Druga opcja"),
                    label = "Wybierz opcje").apply {
                //                selectWidthType = SELECTWIDTHTYPE.FIT
                emptyOption = true
            }, required = true)
            add("spinner", Spinner(label = "Spinner"), required = true)
            add("radiogroup", RadioGroup(listOf("o1" to "Pierwsza opcja", "o2" to "Druga opcja"),
                    inline = true, label = "Radio group").apply {
                setEventListener<RadioGroup> {
                    change = { e ->
                        println(self.value)
                    }
                }
            }, required = true)

            validator = {
                val result = it["text"] == it["textarea"]
                if (!result) {
                    it.getControl("text")?.validatorError = "Niezgodne dane"
                    it.getControl("textarea")?.validatorError = "Niezgodne dane"
                }
                result
            }
            validatorMessage = { "Pole Tekst i Obszar muszą być takie same!" }
        }
        root.add(formPanel)
        val spinner = SpinnerInput(15.05, min = -100000, max = 100000, decimals = 4, forceType = FORCETYPE.ROUND, step = 0.0001).apply {
            size = INPUTSIZE.LARGE
        }
        val ttt = TextInput(value = "abc").apply {
            size = INPUTSIZE.LARGE
        }
        spinner.setEventListener<SpinnerInput> {
            onMinBsSpinner = { e ->
                console.log(e)
            }
            onMaxBsSpinner = { e ->
                console.log(e)
            }
        }
        val formButton = Button("Pokaż dane").setEventListener<Button> {
            click = {
                formPanel.getControl("radiogroup")?.disabled = true
                console.log(formPanel.validate())
                console.log(formPanel.getData().map.toString())
                formPanel.setData(Formularz(mapOf("checkbox" to false, "select" to "a", "radiogroup" to "o2")))
                spinner.toggleVisible()
                spinner.max = spinner.max.plus(2)
                ttt.toggleVisible()
            }
        }
        formPanel.add(formButton)
        spinner.setEventListener<SpinnerInput> {
            change = {
                console.log(spinner.value)
            }
        }
        root.add(spinner)
        root.add(ttt)

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
        ), "flag").apply { dropup = true }
        root.add(dd2)
        dd2.setEventListener<DropDown> {
            hideBsDropdown = { e -> println("hide" + e.detail) }
            hiddenBsDropdown = { e -> println("hidden" + e.detail) }
        }

        val ddbutton = Button("Toggle").setEventListener<Button> {
            click = {
                console.log("x")
                dd2.toggle()
                checkbox.value = true
            }
        }
        root.add(ddbutton)

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
                println(model.collection)
                println(self.text)
                println(textField.value)
//                println(checkbox.value)
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
                if (htmlArea.visible) {
                    htmlArea.hide()
                } else {
                    htmlArea.show()
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
        val button3 = Button("To jest przycisk IMG").apply { image = Img("kotlin.png") }
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
        val v = patch(child, vnode)
        val vnode2 = virtualize("<a href='/top' target='_top'>Test2</a>")
        patch(v, vnode2)*/
    }

    override fun dispose(): Map<String, Any> {
        return mapOf()
    }
}
