package com.example

import pl.treksoft.kvision.form.FormPanel
import pl.treksoft.kvision.form.check.CheckBox
import pl.treksoft.kvision.form.check.Radio
import pl.treksoft.kvision.form.check.RadioGroup
import pl.treksoft.kvision.form.select.AjaxOptions
import pl.treksoft.kvision.form.select.Select
import pl.treksoft.kvision.form.spinner.Spinner
import pl.treksoft.kvision.form.text.Password
import pl.treksoft.kvision.form.text.RichText
import pl.treksoft.kvision.form.text.Text
import pl.treksoft.kvision.form.text.TextArea
import pl.treksoft.kvision.form.time.DateTime
import pl.treksoft.kvision.html.BUTTONSTYLE
import pl.treksoft.kvision.html.Button
import pl.treksoft.kvision.modal.Alert
import pl.treksoft.kvision.modal.Confirm
import pl.treksoft.kvision.panel.HPanel
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.obj
import pl.treksoft.kvision.utils.px
import kotlin.js.Date

class Form(val map: Map<String, Any?>) {
    val text: String? by map
    val password: String? by map
    val password2: String? by map
    val textarea: String? by map
    val richtext: String? by map
    val date: Date? by map
    val time: Date? by map
    val checkbox: Boolean by map
    val radio: Boolean by map
    val select: String? by map
    val spinner: Double? by map
    val radiogroup: String? by map
}


class FormTab : SimplePanel() {
    init {
        this.marginTop = 10.px()
        val formPanel = FormPanel {
            Form(it)
        }.apply {
            add(
                "text",
                Text(label = "Required text field with regexp [0-9] validator").apply {
                    placeholder = "Enter your age"
                },
                required = true,
                validatorMessage = { "Only numbers are allowed" }) {
                it.getValue()?.matches("^[0-9]+$")
            }
            add("password", Password(label = "Password field with minimum length validator"),
                validatorMessage = { "Password too short" }) {
                (it.getValue()?.length ?: 0) >= 8
            }
            add("password2", Password(label = "Password confirmation"),
                validatorMessage = { "Password too short" }) {
                (it.getValue()?.length ?: 0) >= 8
            }
            add("textarea", TextArea(label = "Text area field"))
            add(
                "richtext",
                RichText(label = "Rich text field with a placeholder").apply { placeholder = "Add some info" })
            add(
                "date",
                DateTime(format = "YYYY-MM-DD", label = "Date field with a placeholder").apply {
                    placeholder = "Enter date"
                })
            add(
                "time",
                DateTime(format = "HH:mm", label = "Time field")
            )
            add("checkbox", CheckBox(label = "Required checkbox")) { it.getValue() }
            add("radio", Radio(label = "Radio button"))
            add(
                "select", Select(
                    options = listOf("first" to "First option", "second" to "Second option"),
                    label = "Simple select"
                )
            )

            add("ajaxselect", Select(label = "Select with remote data source").apply {
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
            })
            add("spinner", Spinner(label = "Spinner field 10 - 20", min = 10, max = 20))
            add(
                "radiogroup", RadioGroup(
                    listOf("option1" to "First option", "option2" to "Second option"),
                    inline = true, label = "Radio button group"
                )
            )
            validator = {
                val result = it["password"] == it["password2"]
                if (!result) {
                    it.getControl("password")?.validatorError = "Passwords are not the same"
                    it.getControl("password2")?.validatorError = "Passwords are not the same"
                }
                result
            }
            validatorMessage = { "The passwords are not the same." }
        }
        this.add(formPanel)
        val buttonsPanel = HPanel(spacing = 10)
        val validButton = Button("Validate", "fa-check", BUTTONSTYLE.INFO).onClick {
            formPanel.validate()
        }
        buttonsPanel.add(validButton)
        val dataButton = Button("Show data", "fa-info", BUTTONSTYLE.SUCCESS).onClick {
            Alert.show("Form data in plain JSON", JSON.stringify(formPanel.getDataJson(), space = 1))
        }
        buttonsPanel.add(dataButton)
        val clearButton = Button("Clear data", "fa-times", BUTTONSTYLE.DANGER).onClick {
            Confirm.show("Are you sure?", "Do you want to clear your data?") {
                formPanel.clearData()
            }
        }
        buttonsPanel.add(clearButton)
        formPanel.add(buttonsPanel)
    }
}