/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kvision.core

enum class BsBorder(override val className: String) : CssClass {
    BORDER("border"),
    BORDERTOP("border-top"),
    BORDERBOTTOM("border-bottom"),
    BORDERRIGHT("border-end"),
    BORDERLEFT("border-start"),
    BORDER_0("border-0"),
    BORDERTOP_0("border-top-0"),
    BORDERBOTTOM_0("border-bottom-0"),
    BORDERRIGHT_0("border-end-0"),
    BORDERLEFT_0("border-start-0"),
    BORDERPRIMARY("border-primary"),
    BORDERSECONDARY("border-secondary"),
    BORDERSUCCESS("border-success"),
    BORDERDANGER("border-danger"),
    BORDERWARNING("border-warning"),
    BORDERINFO("border-info"),
    BORDERLIGHT("border-light"),
    BORDERDARK("border-dark"),
    BORDERWHITE("border-white")
}

fun Component.addBsBorder(vararg bsBorder: BsBorder) {
    bsBorder.forEach {
        this.addCssClass(it.className)
    }
}

fun Component.removeBsBorder(vararg bsBorder: BsBorder) {
    bsBorder.forEach {
        this.removeCssClass(it.className)
    }
}

enum class BsRounded(override val className: String) : CssClass {
    ROUNDED("rounded"),
    ROUNDEDTOP("rounded-top"),
    ROUNDEDBOTTOM("rounded-bottom"),
    ROUNDEDLEFT("rounded-start"),
    ROUNDEDRIGHT("rounded-end"),
    ROUNDEDCIRCLE("rounded-circle"),
    ROUNDEDPILL("rounded-pill"),
    ROUNDED0("rounded-0"),
    ROUNDED1("rounded-1"),
    ROUNDED2("rounded-2"),
    ROUNDED3("rounded-3"),
    ROUNDED4("rounded-4"),
    ROUNDED5("rounded-5"),
}

fun Component.addBsRounded(vararg bsRounded: BsRounded) {
    bsRounded.forEach {
        this.addCssClass(it.className)
    }
}

fun Component.removeBsRounded(vararg bsRounded: BsRounded) {
    bsRounded.forEach {
        this.removeCssClass(it.className)
    }
}

fun Component.addBsClearfix() {
    this.addCssClass("clearfix")
}

fun Component.removeBsClearfix() {
    this.removeCssClass("clearfix")
}

enum class BsColor(override val className: String) : CssClass {
    PRIMARY("text-primary"),
    SECONDARY("text-secondary"),
    SUCCESS("text-success"),
    DANGER("text-danger"),
    WARNING("text-warning"),
    INFO("text-info"),
    LIGHT("text-light"),
    DARK("text-dark"),
    WHITE("text-white"),
    BODY("text-body"),
    MUTED("text-muted"),
    BLACK50("text-black-50"),
    WHITE50("text-white-50"),
    PRIMARYBG("text-bg-primary"),
    SECONDARYBG("text-bg-secondary"),
    SUCCESSBG("text-bg-success"),
    DANGERBG("text-bg-danger"),
    WARNINGBG("text-bg-warning"),
    INFOBG("text-bg-info"),
    LIGHTBG("text-bg-light"),
    DARKBG("text-bg-dark")
}

fun Component.addBsColor(bsColor: BsColor) {
    this.addCssClass(bsColor.className)
}

fun Component.removeBsColor(bsColor: BsColor) {
    this.removeCssClass(bsColor.className)
}

enum class BsBgColor(override val className: String) : CssClass {
    PRIMARY("bg-primary"),
    SECONDARY("bg-secondary"),
    SUCCESS("bg-success"),
    DANGER("bg-danger"),
    WARNING("bg-warning"),
    INFO("bg-info"),
    LIGHT("bg-light"),
    DARK("bg-dark"),
    WHITE("bg-white"),
    TRANSPARENT("bg-transparent")
}

fun Component.addBsBgColor(bsBgColor: BsBgColor) {
    this.addCssClass(bsBgColor.className)
}

fun Component.removeBsBgColor(bsBgColor: BsBgColor) {
    this.removeCssClass(bsBgColor.className)
}
