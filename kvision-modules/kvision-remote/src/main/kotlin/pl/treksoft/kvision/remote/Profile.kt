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
package pl.treksoft.kvision.remote

import kotlinx.serialization.Serializable

/**
 * A user profile.
 */
@Serializable
actual data class Profile(
    val id: String? = null,
    val attributes: MutableMap<String, String> = mutableMapOf(),
    val authenticationAttributes: MutableMap<String, String> = mutableMapOf(),
    val roles: MutableSet<String> = mutableSetOf(),
    val permissions: MutableSet<String> = mutableSetOf(),
    val linkedId: String? = null,
    val remembered: Boolean = false,
    val clientName: String? = null
) {
    var username: String?
        get() = attributes["username"]
        set(value) {
            if (value != null) {
                attributes["username"] = value
            } else {
                attributes.remove("username")
            }
        }
    var firstName: String?
        get() = attributes["first_name"]
        set(value) {
            if (value != null) {
                attributes["first_name"] = value
            } else {
                attributes.remove("first_name")
            }
        }
    var familyName: String?
        get() = attributes["family_name"]
        set(value) {
            if (value != null) {
                attributes["family_name"] = value
            } else {
                attributes.remove("family_name")
            }
        }
    var displayName: String?
        get() = attributes["display_name"]
        set(value) {
            if (value != null) {
                attributes["display_name"] = value
            } else {
                attributes.remove("display_name")
            }
        }
    var email: String?
        get() = attributes["email"]
        set(value) {
            if (value != null) {
                attributes["email"] = value
            } else {
                attributes.remove("email")
            }
        }
    var pictureUrl: String?
        get() = attributes["picture_url"]
        set(value) {
            if (value != null) {
                attributes["picture_url"] = value
            } else {
                attributes.remove("picture_url")
            }
        }
    var profileUrl: String?
        get() = attributes["profile_url"]
        set(value) {
            if (value != null) {
                attributes["profile_url"] = value
            } else {
                attributes.remove("profile_url")
            }
        }
}
