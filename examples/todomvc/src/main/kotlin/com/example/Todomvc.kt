package com.example

import com.lightningkite.kotlin.observable.list.observableListOf
import org.w3c.dom.get
import org.w3c.dom.set
import pl.treksoft.kvision.ApplicationBase
import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.data.DataComponent
import pl.treksoft.kvision.data.DataContainer
import pl.treksoft.kvision.form.FieldLabel
import pl.treksoft.kvision.form.check.CHECKINPUTTYPE
import pl.treksoft.kvision.form.check.CheckInput
import pl.treksoft.kvision.form.text.TextInput
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.html.LIST
import pl.treksoft.kvision.html.Link
import pl.treksoft.kvision.html.ListTag
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag
import pl.treksoft.kvision.routing.routing
import kotlin.browser.localStorage

const val ENTER_KEY = 13
const val ESCAPE_KEY = 27

class Todo(completed: Boolean, title: String, hidden: Boolean) : DataComponent() {
    var completed: Boolean by obs(completed)
    var title: String by obs(title)
    var hidden: Boolean by obs(hidden)
}

enum class LISTMODE {
    ALL,
    ACTIVE,
    COMPLETED
}

class Todomvc : ApplicationBase() {

    private val model = observableListOf<Todo>()

    private val checkAllInput = CheckInput(classes = setOf("toggle-all")).apply {
        id = "toggle-all"
        onClick {
            val value = this.value
            model.forEach { it.completed = value }
        }
    }
    private val allLink = Link("All", "#!/", classes = setOf("selected"))
    private val activeLink = Link("Active", "#!/active")
    private val completedLink = Link("Completed", "#!/completed")
    private val clearCompletedButton = Button("Clear completed", classes = setOf("clear-completed")).onClick {
        model.filter { it.completed }.forEach { model.remove(it) }
    }

    private val countTag = Tag(TAG.STRONG, "0")
    private val itemsLeftTag = Tag(TAG.SPAN, " items left", classes = setOf("todo-count")).apply {
        add(countTag)
    }
    private var mode: LISTMODE = LISTMODE.ALL

    private val header = genHeader()
    private val main = genMain()
    private val footer = genFooter()

    override fun start(state: Map<String, Any>) {
        val root = Root("todomvc")
        val section = Tag(TAG.SECTION, classes = setOf("todoapp"))
        section.add(this.header)
        section.add(this.main)
        section.add(this.footer)
        root.add(section)
        loadModel()
        checkModel()
        routing.on("/", { _ -> all() })
            .on("/active", { _ -> active() })
            .on("/completed", { _ -> completed() })
            .resolve()
    }

    private fun loadModel() {
        localStorage.get("todos-kvision")?.let {
            JSON.parse<Array<dynamic>>(it).map { Todo(it.completed, it.title, false) }.forEach {
                model.add(it)
            }
        }
    }

    private fun saveModel() {
        val jsonString = model.map {
            val array = listOf("title" to it.title, "completed" to it.completed).toTypedArray()
            JSON.stringify(kotlin.js.json(*array))
        }.toString()
        localStorage.set("todos-kvision", jsonString)
    }

    private fun checkModel() {
        val countActive = model.filter { !it.completed }.size
        val countCompleted = model.filter { it.completed }.size
        this.main.visible = model.isNotEmpty()
        this.footer.visible = model.isNotEmpty()
        this.countTag.text = countActive.toString()
        this.itemsLeftTag.text = when (countActive) {
            1 -> " item left"
            else -> " items left"
        }
        this.checkAllInput.value = (countActive == 0)
        this.clearCompletedButton.visible = countCompleted > 0
        saveModel()
    }

    private fun all() {
        this.mode = LISTMODE.ALL
        this.allLink.addCssClass("selected")
        this.activeLink.removeCssClass("selected")
        this.completedLink.removeCssClass("selected")
        this.model.forEach { it.hidden = false }
    }

    private fun active() {
        this.mode = LISTMODE.ACTIVE
        this.allLink.removeCssClass("selected")
        this.activeLink.addCssClass("selected")
        this.completedLink.removeCssClass("selected")
        this.model.forEach { it.hidden = it.completed }
    }

    private fun completed() {
        this.mode = LISTMODE.COMPLETED
        this.allLink.removeCssClass("selected")
        this.activeLink.removeCssClass("selected")
        this.completedLink.addCssClass("selected")
        this.model.forEach { it.hidden = !it.completed }
    }

    private fun genHeader(): Tag {
        return Tag(TAG.HEADER, classes = setOf("header")).apply {
            add(Tag(TAG.H1, "todos"))
            add(TextInput(classes = setOf("new-todo")).apply {
                placeholder = "What needs to be done?"
                autofocus = true
                setEventListener<TextInput> {
                    keydown = { e ->
                        if (e.keyCode == ENTER_KEY) {
                            addTodo(self.value)
                            self.value = null
                        }
                    }
                }
            })
        }
    }

    private fun addTodo(value: String?) {
        val v = value?.trim() ?: ""
        if (v.isNotEmpty()) {
            model.add(Todo(false, v, mode == LISTMODE.COMPLETED))
        }
    }

    private fun editTodo(index: Int, value: String?) {
        val v = value?.trim() ?: ""
        if (v.isNotEmpty()) {
            model[index].title = v
        } else {
            model.removeAt(index)
        }
    }

    private fun genMain(): Tag {
        return Tag(TAG.SECTION, classes = setOf("main")).apply {
            add(checkAllInput)
            add(FieldLabel("toggle-all", "Mark all as complete"))
            add(DataContainer(model, { index ->
                val li = Tag(TAG.LI)
                li.apply {
                    if (model[index].completed) addCssClass("completed")
                    if (model[index].hidden) addCssClass("hidden")
                    val edit = TextInput(classes = setOf("edit"))
                    val view = Tag(TAG.DIV, classes = setOf("view")).apply {
                        add(CheckInput(
                            CHECKINPUTTYPE.CHECKBOX, model[index].completed, classes = setOf("toggle")
                        ).onClick {
                            model[index].completed = this.value
                            model[index].hidden =
                                    mode == LISTMODE.ACTIVE && this.value || mode == LISTMODE.COMPLETED && !this.value
                        })
                        add(Tag(TAG.LABEL, model[index].title).apply {
                            setEventListener<Tag> {
                                dblclick = {
                                    li.getElementJQuery()?.addClass("editing")
                                    edit.value = model[index].title
                                    edit.getElementJQuery()?.focus()
                                }
                            }
                        })
                        add(Button("", classes = setOf("destroy")).onClick {
                            model.removeAt(index)
                        })
                    }
                    edit.setEventListener<TextInput> {
                        blur = {
                            if (li.getElementJQuery()?.hasClass("editing") == true) {
                                li.getElementJQuery()?.removeClass("editing")
                                editTodo(index, self.value)
                            }
                        }
                        keydown = { e ->
                            if (e.keyCode == ENTER_KEY) {
                                li.getElementJQuery()?.removeClass("editing")
                                editTodo(index, self.value)
                            }
                            if (e.keyCode == ESCAPE_KEY) {
                                li.getElementJQuery()?.removeClass("editing")
                            }
                        }
                    }
                    add(view)
                    add(edit)
                }
            }, Tag(TAG.UL, classes = setOf("todo-list"))).onUpdate {
                checkModel()
            })
        }
    }

    private fun genFooter(): Tag {
        return Tag(TAG.FOOTER, classes = setOf("footer")).apply {
            add(itemsLeftTag)
            add(ListTag(LIST.UL, classes = setOf("filters")).apply {
                add(allLink)
                add(activeLink)
                add(completedLink)
            })
            add(clearCompletedButton)
        }
    }

    override fun dispose(): Map<String, Any> {
        return mapOf()
    }
}
