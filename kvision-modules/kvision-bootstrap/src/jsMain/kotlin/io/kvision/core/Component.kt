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
    BORDERPRIMARYSUBTLE("border-primary-subtle"),
    BORDERSECONDARY("border-secondary"),
    BORDERSECONDARYSUBTLE("border-secondary-subtle"),
    BORDERSUCCESS("border-success"),
    BORDERSUCCESSSUBTLE("border-success-subtle"),
    BORDERDANGER("border-danger"),
    BORDERDANGERSUBTLE("border-danger-subtle"),
    BORDERWARNING("border-warning"),
    BORDERWARNINGSUBTLE("border-warning-subtle"),
    BORDERINFO("border-info"),
    BORDERINFOSUBTLE("border-info-subtle"),
    BORDERLIGHT("border-light"),
    BORDERLIGHTSUBTLE("border-light-subtle"),
    BORDERDARK("border-dark"),
    BORDERDARKSUBTLE("border-dark-subtle"),
    BORDERWHITE("border-white"),
    BORDERBLACK("border-black")
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
    PRIMARYEMPHASIS("text-primary-emphasis"),
    SECONDARY("text-secondary"),
    SECONDARYEMPHASIS("text-secondary-emphasis"),
    SUCCESS("text-success"),
    SUCCESSEMPHASIS("text-success-emphasis"),
    DANGER("text-danger"),
    DANGEREMPHASIS("text-danger-emphasis"),
    WARNING("text-warning"),
    WARNINGEMPHASIS("text-warning-emphasis"),
    INFO("text-info"),
    INFOEMPHASIS("text-info-emphasis"),
    LIGHT("text-light"),
    LIGHTEMPHASIS("text-light-emphasis"),
    DARK("text-dark"),
    DARKEMPHASIS("text-dark-emphasis"),
    BLACK("text-black"),
    WHITE("text-white"),
    BODY("text-body"),
    BODYSECONDARY("text-body-secondary"),
    BODYTERTIARY("text-body-tertiary"),
    BODYEMPHASIS("text-body-emphasis"),
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
    PRIMARYSUBTLE("bg-primary-subtle"),
    SECONDARY("bg-secondary"),
    SECONDARYSUBTLE("bg-secondary-subtle"),
    SUCCESS("bg-success"),
    SUCCESSSUBTLE("bg-success-subtle"),
    DANGER("bg-danger"),
    DANGERSUBTLE("bg-danger-subtle"),
    WARNING("bg-warning"),
    WARNINGSUBTLE("bg-warning-subtle"),
    INFO("bg-info"),
    INFOSUBTLE("bg-info-subtle"),
    LIGHT("bg-light"),
    LIGHTSUBTLE("bg-light-subtle"),
    DARK("bg-dark"),
    DARKSUBTLE("bg-dark-subtle"),
    BODY("bg-body"),
    BODYSECONDARY("bg-body-secondary"),
    BODYTERTIARY("bg-body-tertiary"),
    BLACK("bg-black"),
    WHITE("bg-white"),
    TRANSPARENT("bg-transparent")
}

fun Component.addBsBgColor(bsBgColor: BsBgColor) {
    this.addCssClass(bsBgColor.className)
}

fun Component.removeBsBgColor(bsBgColor: BsBgColor) {
    this.removeCssClass(bsBgColor.className)
}
