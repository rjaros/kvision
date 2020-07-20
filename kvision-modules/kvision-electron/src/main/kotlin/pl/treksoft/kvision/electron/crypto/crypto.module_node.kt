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

@file:JsModule("crypto")
@file:JsNonModule
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS"
)

package pl.treksoft.kvision.electron.crypto

import es5.Object
import node.buffer.Buffer
import org.khronos.webgl.*
import pl.treksoft.kvision.electron.stream.internal.*

@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface Certificate {
    fun exportChallenge(spkac: String): Buffer
    fun exportChallenge(spkac: Uint8Array): Buffer
    fun exportChallenge(spkac: Uint8ClampedArray): Buffer
    fun exportChallenge(spkac: Uint16Array): Buffer
    fun exportChallenge(spkac: Uint32Array): Buffer
    fun exportChallenge(spkac: Int8Array): Buffer
    fun exportChallenge(spkac: Int16Array): Buffer
    fun exportChallenge(spkac: Int32Array): Buffer
    fun exportChallenge(spkac: Float32Array): Buffer
    fun exportChallenge(spkac: Float64Array): Buffer
    fun exportChallenge(spkac: DataView): Buffer
    fun exportPublicKey(spkac: String): Buffer
    fun exportPublicKey(spkac: Uint8Array): Buffer
    fun exportPublicKey(spkac: Uint8ClampedArray): Buffer
    fun exportPublicKey(spkac: Uint16Array): Buffer
    fun exportPublicKey(spkac: Uint32Array): Buffer
    fun exportPublicKey(spkac: Int8Array): Buffer
    fun exportPublicKey(spkac: Int16Array): Buffer
    fun exportPublicKey(spkac: Int32Array): Buffer
    fun exportPublicKey(spkac: Float32Array): Buffer
    fun exportPublicKey(spkac: Float64Array): Buffer
    fun exportPublicKey(spkac: DataView): Buffer
    fun verifySpkac(spkac: Uint8Array): Boolean
    fun verifySpkac(spkac: Uint8ClampedArray): Boolean
    fun verifySpkac(spkac: Uint16Array): Boolean
    fun verifySpkac(spkac: Uint32Array): Boolean
    fun verifySpkac(spkac: Int8Array): Boolean
    fun verifySpkac(spkac: Int16Array): Boolean
    fun verifySpkac(spkac: Int32Array): Boolean
    fun verifySpkac(spkac: Float32Array): Boolean
    fun verifySpkac(spkac: Float64Array): Boolean
    fun verifySpkac(spkac: DataView): Boolean

    companion object {
        @nativeInvoke
        operator fun invoke(): Certificate
    }
}

external interface HashOptions : TransformOptions {
    var outputLength: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external var fips: Boolean

external fun createHash(algorithm: String, options: HashOptions = definedExternally): Hash

external fun createHmac(algorithm: String, key: String, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: Uint8Array, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: Uint8ClampedArray, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: Uint16Array, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: Uint32Array, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: Int8Array, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: Int16Array, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: Int32Array, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: Float32Array, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: Float64Array, options: TransformOptions = definedExternally): Hmac

external fun createHmac(algorithm: String, key: DataView, options: TransformOptions = definedExternally): Hmac

external open class Hash : Transform {
    open fun update(data: String): Hash
    open fun update(data: Uint8Array): Hash
    open fun update(data: Uint8ClampedArray): Hash
    open fun update(data: Uint16Array): Hash
    open fun update(data: Uint32Array): Hash
    open fun update(data: Int8Array): Hash
    open fun update(data: Int16Array): Hash
    open fun update(data: Int32Array): Hash
    open fun update(data: Float32Array): Hash
    open fun update(data: Float64Array): Hash
    open fun update(data: DataView): Hash
    open fun update(data: String, input_encoding: String /* "utf8" | "ascii" | "latin1" */): Hash
    open fun digest(): Buffer
    open fun digest(encoding: String /* "latin1" | "hex" | "base64" */): String
}

external open class Hmac : Transform {
    open fun update(data: String): Hmac
    open fun update(data: Uint8Array): Hmac
    open fun update(data: Uint8ClampedArray): Hmac
    open fun update(data: Uint16Array): Hmac
    open fun update(data: Uint32Array): Hmac
    open fun update(data: Int8Array): Hmac
    open fun update(data: Int16Array): Hmac
    open fun update(data: Int32Array): Hmac
    open fun update(data: Float32Array): Hmac
    open fun update(data: Float64Array): Hmac
    open fun update(data: DataView): Hmac
    open fun update(data: String, input_encoding: String /* "utf8" | "ascii" | "latin1" */): Hmac
    open fun digest(): Buffer
    open fun digest(encoding: String /* "latin1" | "hex" | "base64" */): String
}

external interface KeyExportOptions<T : String> {
    var type: String /* 'pkcs1' | 'spki' | 'pkcs8' | 'sec1' */
    var format: T
    var cipher: String?
        get() = definedExternally
        set(value) = definedExternally
    var passphrase: dynamic /* String? | Buffer? */
        get() = definedExternally
        set(value) = definedExternally
}

external open class KeyObject {
    open var asymmetricKeyType: String /* 'rsa' | 'dsa' | 'ec' */
    open var asymmetricKeySize: Number
    open fun export(options: KeyExportOptions<String /* 'pem' | 'der' */>): dynamic /* Buffer */
    open var symmetricKeySize: Number
    open var type: String /* 'secret' | 'public' | 'private' */
}

external interface CipherCCMOptions : TransformOptions {
    var authTagLength: Number
}

external interface CipherGCMOptions : TransformOptions {
    var authTagLength: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external fun createCipher(
    algorithm: String /* 'aes-128-ccm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherCCMOptions
): CipherCCM

external fun createCipher(
    algorithm: String /* 'aes-192-ccm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherCCMOptions
): CipherCCM

external fun createCipher(
    algorithm: String /* 'aes-256-ccm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherCCMOptions
): CipherCCM

external fun createCipher(
    algorithm: String /* 'aes-128-gcm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherGCMOptions = definedExternally
): CipherGCM

external fun createCipher(
    algorithm: String /* 'aes-192-gcm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherGCMOptions = definedExternally
): CipherGCM

external fun createCipher(
    algorithm: String /* 'aes-256-gcm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherGCMOptions = definedExternally
): CipherGCM

external fun createCipher(algorithm: String, password: String, options: TransformOptions = definedExternally): Cipher

external fun createCipher(
    algorithm: String,
    password: Uint8Array,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipher(
    algorithm: String,
    password: Uint8ClampedArray,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipher(
    algorithm: String,
    password: Uint16Array,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipher(
    algorithm: String,
    password: Uint32Array,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipher(algorithm: String, password: Int8Array, options: TransformOptions = definedExternally): Cipher

external fun createCipher(
    algorithm: String,
    password: Int16Array,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipher(
    algorithm: String,
    password: Int32Array,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipher(
    algorithm: String,
    password: Float32Array,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipher(
    algorithm: String,
    password: Float64Array,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipher(algorithm: String, password: DataView, options: TransformOptions = definedExternally): Cipher

external fun createCipheriv(
    algorithm: String /* 'aes-128-ccm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView | KeyObject */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherCCMOptions
): CipherCCM

external fun createCipheriv(
    algorithm: String /* 'aes-192-ccm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView | KeyObject */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherCCMOptions
): CipherCCM

external fun createCipheriv(
    algorithm: String /* 'aes-256-ccm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView | KeyObject */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherCCMOptions
): CipherCCM

external fun createCipheriv(
    algorithm: String /* 'aes-128-gcm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView | KeyObject */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherGCMOptions = definedExternally
): CipherGCM

external fun createCipheriv(
    algorithm: String /* 'aes-192-gcm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView | KeyObject */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherGCMOptions = definedExternally
): CipherGCM

external fun createCipheriv(
    algorithm: String /* 'aes-256-gcm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView | KeyObject */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherGCMOptions = definedExternally
): CipherGCM

external fun createCipheriv(
    algorithm: String,
    key: String,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: Uint8Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: Uint8ClampedArray,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: Uint16Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: Uint32Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: Int8Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: Int16Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: Int32Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: Float32Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: Float64Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: DataView,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external fun createCipheriv(
    algorithm: String,
    key: KeyObject,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Cipher

external open class Cipher : Transform {
    open fun update(data: String): Buffer
    open fun update(data: Uint8Array): Buffer
    open fun update(data: Uint8ClampedArray): Buffer
    open fun update(data: Uint16Array): Buffer
    open fun update(data: Uint32Array): Buffer
    open fun update(data: Int8Array): Buffer
    open fun update(data: Int16Array): Buffer
    open fun update(data: Int32Array): Buffer
    open fun update(data: Float32Array): Buffer
    open fun update(data: Float64Array): Buffer
    open fun update(data: DataView): Buffer
    open fun update(data: String, input_encoding: String /* "utf8" | "ascii" | "binary" */): Buffer
    open fun update(
        data: Uint8Array,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: Uint8ClampedArray,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: Uint16Array,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: Uint32Array,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: Int8Array,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: Int16Array,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: Int32Array,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: Float32Array,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: Float64Array,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: DataView,
        input_encoding: Nothing?,
        output_encoding: String /* "binary" | "base64" | "hex" */
    ): String

    open fun update(
        data: String,
        input_encoding: String /* "utf8" | "ascii" | "ascii" | "ascii" | "binary" | "binary" | "binary" */,
        output_encoding: String /* "binary" | "base64" | "hex" | "binary" | "base64" | "hex" | "binary" | "base64" | "hex" */
    ): String

    open fun final(): Buffer
    open fun final(output_encoding: String): String
    open fun setAutoPadding(auto_padding: Boolean = definedExternally): Cipher /* this */
}

external interface `T$27` {
    var plaintextLength: Number
}

external interface CipherCCM : Cipher {
    fun setAAD(buffer: Buffer, options: `T$27`): CipherCCM /* this */
    fun getAuthTag(): Buffer
}

external interface CipherGCM : Cipher {
    fun setAAD(buffer: Buffer, options: `T$27` = definedExternally): CipherGCM /* this */
    fun getAuthTag(): Buffer
}

external fun createDecipher(
    algorithm: String /* 'aes-128-ccm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherCCMOptions
): DecipherCCM

external fun createDecipher(
    algorithm: String /* 'aes-192-ccm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherCCMOptions
): DecipherCCM

external fun createDecipher(
    algorithm: String /* 'aes-256-ccm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherCCMOptions
): DecipherCCM

external fun createDecipher(
    algorithm: String /* 'aes-128-gcm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherGCMOptions = definedExternally
): DecipherGCM

external fun createDecipher(
    algorithm: String /* 'aes-192-gcm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherGCMOptions = definedExternally
): DecipherGCM

external fun createDecipher(
    algorithm: String /* 'aes-256-gcm' */,
    password: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    options: CipherGCMOptions = definedExternally
): DecipherGCM

external fun createDecipher(
    algorithm: String,
    password: String,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: Uint8Array,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: Uint8ClampedArray,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: Uint16Array,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: Uint32Array,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: Int8Array,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: Int16Array,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: Int32Array,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: Float32Array,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: Float64Array,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipher(
    algorithm: String,
    password: DataView,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String /* 'aes-128-ccm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherCCMOptions
): DecipherCCM

external fun createDecipheriv(
    algorithm: String /* 'aes-192-ccm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherCCMOptions
): DecipherCCM

external fun createDecipheriv(
    algorithm: String /* 'aes-256-ccm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherCCMOptions
): DecipherCCM

external fun createDecipheriv(
    algorithm: String /* 'aes-128-gcm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherGCMOptions = definedExternally
): DecipherGCM

external fun createDecipheriv(
    algorithm: String /* 'aes-192-gcm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherGCMOptions = definedExternally
): DecipherGCM

external fun createDecipheriv(
    algorithm: String /* 'aes-256-gcm' */,
    key: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: CipherGCMOptions = definedExternally
): DecipherGCM

external fun createDecipheriv(
    algorithm: String,
    key: String,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: Uint8Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: Uint8ClampedArray,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: Uint16Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: Uint32Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: Int8Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: Int16Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: Int32Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: Float32Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: Float64Array,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external fun createDecipheriv(
    algorithm: String,
    key: DataView,
    iv: dynamic /* String? | Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? | DataView? */,
    options: TransformOptions = definedExternally
): Decipher

external open class Decipher : Transform {
    open fun update(data: Uint8Array): Buffer
    open fun update(data: Uint8ClampedArray): Buffer
    open fun update(data: Uint16Array): Buffer
    open fun update(data: Uint32Array): Buffer
    open fun update(data: Int8Array): Buffer
    open fun update(data: Int16Array): Buffer
    open fun update(data: Int32Array): Buffer
    open fun update(data: Float32Array): Buffer
    open fun update(data: Float64Array): Buffer
    open fun update(data: DataView): Buffer
    open fun update(data: String, input_encoding: String /* "binary" | "base64" | "hex" */): Buffer
    open fun update(
        data: Uint8Array,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: Uint8ClampedArray,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: Uint16Array,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: Uint32Array,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: Int8Array,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: Int16Array,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: Int32Array,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: Float32Array,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: Float64Array,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: DataView,
        input_encoding: String /* "binary" | "base64" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" */
    ): String

    open fun update(
        data: String,
        input_encoding: String /* "binary" | "base64" | "base64" | "base64" | "hex" | "hex" | "hex" */,
        output_encoding: String /* "utf8" | "ascii" | "binary" | "utf8" | "ascii" | "binary" | "utf8" | "ascii" | "binary" */
    ): String

    open fun final(): Buffer
    open fun final(output_encoding: String): String
    open fun setAutoPadding(auto_padding: Boolean = definedExternally): Decipher /* this */
}

external interface DecipherCCM : Decipher {
    fun setAuthTag(buffer: Uint8Array): DecipherCCM /* this */
    fun setAuthTag(buffer: Uint8ClampedArray): DecipherCCM /* this */
    fun setAuthTag(buffer: Uint16Array): DecipherCCM /* this */
    fun setAuthTag(buffer: Uint32Array): DecipherCCM /* this */
    fun setAuthTag(buffer: Int8Array): DecipherCCM /* this */
    fun setAuthTag(buffer: Int16Array): DecipherCCM /* this */
    fun setAuthTag(buffer: Int32Array): DecipherCCM /* this */
    fun setAuthTag(buffer: Float32Array): DecipherCCM /* this */
    fun setAuthTag(buffer: Float64Array): DecipherCCM /* this */
    fun setAuthTag(buffer: DataView): DecipherCCM /* this */
    fun setAAD(buffer: Uint8Array, options: `T$27`): DecipherCCM /* this */
    fun setAAD(buffer: Uint8ClampedArray, options: `T$27`): DecipherCCM /* this */
    fun setAAD(buffer: Uint16Array, options: `T$27`): DecipherCCM /* this */
    fun setAAD(buffer: Uint32Array, options: `T$27`): DecipherCCM /* this */
    fun setAAD(buffer: Int8Array, options: `T$27`): DecipherCCM /* this */
    fun setAAD(buffer: Int16Array, options: `T$27`): DecipherCCM /* this */
    fun setAAD(buffer: Int32Array, options: `T$27`): DecipherCCM /* this */
    fun setAAD(buffer: Float32Array, options: `T$27`): DecipherCCM /* this */
    fun setAAD(buffer: Float64Array, options: `T$27`): DecipherCCM /* this */
    fun setAAD(buffer: DataView, options: `T$27`): DecipherCCM /* this */
}

external interface DecipherGCM : Decipher {
    fun setAuthTag(buffer: Uint8Array): DecipherGCM /* this */
    fun setAuthTag(buffer: Uint8ClampedArray): DecipherGCM /* this */
    fun setAuthTag(buffer: Uint16Array): DecipherGCM /* this */
    fun setAuthTag(buffer: Uint32Array): DecipherGCM /* this */
    fun setAuthTag(buffer: Int8Array): DecipherGCM /* this */
    fun setAuthTag(buffer: Int16Array): DecipherGCM /* this */
    fun setAuthTag(buffer: Int32Array): DecipherGCM /* this */
    fun setAuthTag(buffer: Float32Array): DecipherGCM /* this */
    fun setAuthTag(buffer: Float64Array): DecipherGCM /* this */
    fun setAuthTag(buffer: DataView): DecipherGCM /* this */
    fun setAAD(buffer: Uint8Array, options: `T$27` = definedExternally): DecipherGCM /* this */
    fun setAAD(buffer: Uint8ClampedArray, options: `T$27` = definedExternally): DecipherGCM /* this */
    fun setAAD(buffer: Uint16Array, options: `T$27` = definedExternally): DecipherGCM /* this */
    fun setAAD(buffer: Uint32Array, options: `T$27` = definedExternally): DecipherGCM /* this */
    fun setAAD(buffer: Int8Array, options: `T$27` = definedExternally): DecipherGCM /* this */
    fun setAAD(buffer: Int16Array, options: `T$27` = definedExternally): DecipherGCM /* this */
    fun setAAD(buffer: Int32Array, options: `T$27` = definedExternally): DecipherGCM /* this */
    fun setAAD(buffer: Float32Array, options: `T$27` = definedExternally): DecipherGCM /* this */
    fun setAAD(buffer: Float64Array, options: `T$27` = definedExternally): DecipherGCM /* this */
    fun setAAD(buffer: DataView, options: `T$27` = definedExternally): DecipherGCM /* this */
}

external interface PrivateKeyInput {
    var key: dynamic /* String | Buffer */
        get() = definedExternally
        set(value) = definedExternally
    var format: String? /* 'pem' | 'der' */
        get() = definedExternally
        set(value) = definedExternally
    var type: String? /* 'pkcs1' | 'pkcs8' | 'sec1' */
        get() = definedExternally
        set(value) = definedExternally
    var passphrase: dynamic /* String? | Buffer? */
        get() = definedExternally
        set(value) = definedExternally
}

external interface PublicKeyInput {
    var key: dynamic /* String | Buffer */
        get() = definedExternally
        set(value) = definedExternally
    var format: String? /* 'pem' | 'der' */
        get() = definedExternally
        set(value) = definedExternally
    var type: String? /* 'pkcs1' | 'spki' */
        get() = definedExternally
        set(value) = definedExternally
}

external fun createPrivateKey(key: PrivateKeyInput): KeyObject

external fun createPrivateKey(key: String): KeyObject

external fun createPrivateKey(key: Buffer): KeyObject

external fun createPublicKey(key: PublicKeyInput): KeyObject

external fun createPublicKey(key: String): KeyObject

external fun createPublicKey(key: Buffer): KeyObject

external fun createPublicKey(key: KeyObject): KeyObject

external fun createSecretKey(key: Buffer): KeyObject

external fun createSign(algorithm: String, options: WritableOptions = definedExternally): Signer

external interface SigningOptions {
    var padding: Number?
        get() = definedExternally
        set(value) = definedExternally
    var saltLength: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SignPrivateKeyInput : PrivateKeyInput, SigningOptions

external open class Signer : Writable {
    open fun update(data: String): Signer
    open fun update(data: Uint8Array): Signer
    open fun update(data: Uint8ClampedArray): Signer
    open fun update(data: Uint16Array): Signer
    open fun update(data: Uint32Array): Signer
    open fun update(data: Int8Array): Signer
    open fun update(data: Int16Array): Signer
    open fun update(data: Int32Array): Signer
    open fun update(data: Float32Array): Signer
    open fun update(data: Float64Array): Signer
    open fun update(data: DataView): Signer
    open fun update(data: String, input_encoding: String /* "utf8" | "ascii" | "latin1" */): Signer
    open fun sign(private_key: SignPrivateKeyInput): Buffer
    open fun sign(private_key: String): Buffer
    open fun sign(private_key: Buffer): Buffer
    open fun sign(private_key: KeyObject): Buffer
    open fun sign(private_key: SignPrivateKeyInput, output_format: String /* "latin1" | "hex" | "base64" */): String
    open fun sign(private_key: String, output_format: String /* "latin1" | "hex" | "base64" */): String
    open fun sign(private_key: Buffer, output_format: String /* "latin1" | "hex" | "base64" */): String
    open fun sign(private_key: KeyObject, output_format: String /* "latin1" | "hex" | "base64" */): String
}

external fun createVerify(algorithm: String, options: WritableOptions = definedExternally): Verify

external open class Verify : Writable {
    open fun update(data: String): Verify
    open fun update(data: Uint8Array): Verify
    open fun update(data: Uint8ClampedArray): Verify
    open fun update(data: Uint16Array): Verify
    open fun update(data: Uint32Array): Verify
    open fun update(data: Int8Array): Verify
    open fun update(data: Int16Array): Verify
    open fun update(data: Int32Array): Verify
    open fun update(data: Float32Array): Verify
    open fun update(data: Float64Array): Verify
    open fun update(data: DataView): Verify
    open fun update(data: String, input_encoding: String /* "utf8" | "ascii" | "latin1" */): Verify
    open fun verify(
        obj: Object,
        signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
    ): Boolean

    open fun verify(
        obj: String,
        signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
    ): Boolean

    open fun verify(
        obj: Buffer,
        signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
    ): Boolean

    open fun verify(
        obj: KeyObject,
        signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
    ): Boolean

    open fun verify(
        obj: Object,
        signature: String,
        signature_format: String /* "latin1" | "hex" | "base64" */ = definedExternally
    ): Boolean

    open fun verify(
        obj: String,
        signature: String,
        signature_format: String /* "latin1" | "hex" | "base64" */ = definedExternally
    ): Boolean

    open fun verify(
        obj: Buffer,
        signature: String,
        signature_format: String /* "latin1" | "hex" | "base64" */ = definedExternally
    ): Boolean

    open fun verify(
        obj: KeyObject,
        signature: String,
        signature_format: String /* "latin1" | "hex" | "base64" */ = definedExternally
    ): Boolean
}

external fun createDiffieHellman(prime_length: Number, generator: Number = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: Uint8Array = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: Uint8ClampedArray = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: Uint16Array = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: Uint32Array = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: Int8Array = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: Int16Array = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: Int32Array = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: Float32Array = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: Float64Array = definedExternally): DiffieHellman

external fun createDiffieHellman(prime_length: Number, generator: DataView = definedExternally): DiffieHellman

external fun createDiffieHellman(prime: Uint8Array): DiffieHellman

external fun createDiffieHellman(prime: Uint8ClampedArray): DiffieHellman

external fun createDiffieHellman(prime: Uint16Array): DiffieHellman

external fun createDiffieHellman(prime: Uint32Array): DiffieHellman

external fun createDiffieHellman(prime: Int8Array): DiffieHellman

external fun createDiffieHellman(prime: Int16Array): DiffieHellman

external fun createDiffieHellman(prime: Int32Array): DiffieHellman

external fun createDiffieHellman(prime: Float32Array): DiffieHellman

external fun createDiffieHellman(prime: Float64Array): DiffieHellman

external fun createDiffieHellman(prime: DataView): DiffieHellman

external fun createDiffieHellman(prime: String, prime_encoding: String /* "latin1" */): DiffieHellman

external fun createDiffieHellman(prime: String, prime_encoding: String /* "hex" */): DiffieHellman

external fun createDiffieHellman(prime: String, prime_encoding: String /* "base64" */): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "latin1" */,
    generator: dynamic /* Number | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "hex" */,
    generator: dynamic /* Number | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "base64" */,
    generator: dynamic /* Number | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "latin1" */,
    generator: String,
    generator_encoding: String /* "latin1" */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "latin1" */,
    generator: String,
    generator_encoding: String /* "hex" */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "latin1" */,
    generator: String,
    generator_encoding: String /* "base64" */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "hex" */,
    generator: String,
    generator_encoding: String /* "latin1" */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "hex" */,
    generator: String,
    generator_encoding: String /* "hex" */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "hex" */,
    generator: String,
    generator_encoding: String /* "base64" */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "base64" */,
    generator: String,
    generator_encoding: String /* "latin1" */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "base64" */,
    generator: String,
    generator_encoding: String /* "hex" */
): DiffieHellman

external fun createDiffieHellman(
    prime: String,
    prime_encoding: String /* "base64" */,
    generator: String,
    generator_encoding: String /* "base64" */
): DiffieHellman

external open class DiffieHellman {
    open fun generateKeys(): Buffer
    open fun generateKeys(encoding: String /* "latin1" | "hex" | "base64" */): String
    open fun computeSecret(other_public_key: Uint8Array): Buffer
    open fun computeSecret(other_public_key: Uint8ClampedArray): Buffer
    open fun computeSecret(other_public_key: Uint16Array): Buffer
    open fun computeSecret(other_public_key: Uint32Array): Buffer
    open fun computeSecret(other_public_key: Int8Array): Buffer
    open fun computeSecret(other_public_key: Int16Array): Buffer
    open fun computeSecret(other_public_key: Int32Array): Buffer
    open fun computeSecret(other_public_key: Float32Array): Buffer
    open fun computeSecret(other_public_key: Float64Array): Buffer
    open fun computeSecret(other_public_key: DataView): Buffer
    open fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" | "hex" | "base64" */): Buffer
    open fun computeSecret(
        other_public_key: Uint8Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Uint8ClampedArray,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Uint16Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Uint32Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Int8Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Int16Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Int32Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Float32Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Float64Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: DataView,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: String,
        input_encoding: String /* "latin1" | "hex" | "hex" | "hex" | "base64" | "base64" | "base64" */,
        output_encoding: String /* "latin1" | "hex" | "base64" | "latin1" | "hex" | "base64" | "latin1" | "hex" | "base64" */
    ): String

    open fun getPrime(): Buffer
    open fun getPrime(encoding: String /* "latin1" | "hex" | "base64" */): String
    open fun getGenerator(): Buffer
    open fun getGenerator(encoding: String /* "latin1" | "hex" | "base64" */): String
    open fun getPublicKey(): Buffer
    open fun getPublicKey(encoding: String /* "latin1" | "hex" | "base64" */): String
    open fun getPrivateKey(): Buffer
    open fun getPrivateKey(encoding: String /* "latin1" | "hex" | "base64" */): String
    open fun setPublicKey(public_key: Uint8Array)
    open fun setPublicKey(public_key: Uint8ClampedArray)
    open fun setPublicKey(public_key: Uint16Array)
    open fun setPublicKey(public_key: Uint32Array)
    open fun setPublicKey(public_key: Int8Array)
    open fun setPublicKey(public_key: Int16Array)
    open fun setPublicKey(public_key: Int32Array)
    open fun setPublicKey(public_key: Float32Array)
    open fun setPublicKey(public_key: Float64Array)
    open fun setPublicKey(public_key: DataView)
    open fun setPublicKey(public_key: String, encoding: String)
    open fun setPrivateKey(private_key: Uint8Array)
    open fun setPrivateKey(private_key: Uint8ClampedArray)
    open fun setPrivateKey(private_key: Uint16Array)
    open fun setPrivateKey(private_key: Uint32Array)
    open fun setPrivateKey(private_key: Int8Array)
    open fun setPrivateKey(private_key: Int16Array)
    open fun setPrivateKey(private_key: Int32Array)
    open fun setPrivateKey(private_key: Float32Array)
    open fun setPrivateKey(private_key: Float64Array)
    open fun setPrivateKey(private_key: DataView)
    open fun setPrivateKey(private_key: String, encoding: String)
    open var verifyError: Number
}

external fun getDiffieHellman(group_name: String): DiffieHellman

external fun pbkdf2(
    password: String,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: Uint8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: Uint8ClampedArray,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: Uint16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: Uint32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: Int8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: Int16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: Int32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: Float32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: Float64Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2(
    password: DataView,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String,
    callback: (err: Error?, derivedKey: Buffer) -> Any
)

external fun pbkdf2Sync(
    password: String,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: Uint8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: Uint8ClampedArray,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: Uint16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: Uint32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: Int8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: Int16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: Int32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: Float32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: Float64Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun pbkdf2Sync(
    password: DataView,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    iterations: Number,
    keylen: Number,
    digest: String
): Buffer

external fun randomBytes(size: Number): Buffer

external fun randomBytes(size: Number, callback: (err: Error?, buf: Buffer) -> Unit)

external fun pseudoRandomBytes(size: Number): Buffer

external fun pseudoRandomBytes(size: Number, callback: (err: Error?, buf: Buffer) -> Unit)

external fun <T> randomFillSync(buffer: T, offset: Number = definedExternally, size: Number = definedExternally): T

external fun <T> randomFill(buffer: T, callback: (err: Error?, buf: T) -> Unit)

external fun <T> randomFill(buffer: T, offset: Number, callback: (err: Error?, buf: T) -> Unit)

external fun <T> randomFill(buffer: T, offset: Number, size: Number, callback: (err: Error?, buf: T) -> Unit)

external interface ScryptOptions {
    var N: Number?
        get() = definedExternally
        set(value) = definedExternally
    var r: Number?
        get() = definedExternally
        set(value) = definedExternally
    var p: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxmem: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external fun scrypt(
    password: String,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Uint8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Uint8ClampedArray,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Uint16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Uint32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Int8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Int16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Int32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Float32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Float64Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: DataView,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: String,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Uint8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Uint8ClampedArray,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Uint16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Uint32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Int8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Int16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Int32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Float32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: Float64Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scrypt(
    password: DataView,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions,
    callback: (err: Error?, derivedKey: Buffer) -> Unit
)

external fun scryptSync(
    password: String,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: Uint8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: Uint8ClampedArray,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: Uint16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: Uint32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: Int8Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: Int16Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: Int32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: Float32Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: Float64Array,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external fun scryptSync(
    password: DataView,
    salt: dynamic /* String | Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */,
    keylen: Number,
    options: ScryptOptions = definedExternally
): Buffer

external interface RsaPublicKey {
    var key: dynamic /* String | Buffer | KeyObject */
        get() = definedExternally
        set(value) = definedExternally
    var padding: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RsaPrivateKey {
    var key: dynamic /* String | Buffer | KeyObject */
        get() = definedExternally
        set(value) = definedExternally
    var passphrase: String?
        get() = definedExternally
        set(value) = definedExternally
    var oaepHash: String?
        get() = definedExternally
        set(value) = definedExternally
    var oaepLabel: dynamic /* Uint8Array? | Uint8ClampedArray? | Uint16Array? | Uint32Array? | Int8Array? | Int16Array? | Int32Array? | Float32Array? | Float64Array? */
        get() = definedExternally
        set(value) = definedExternally
    var padding: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external fun publicEncrypt(
    key: RsaPublicKey,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun publicEncrypt(
    key: RsaPrivateKey,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun publicEncrypt(
    key: String,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun publicEncrypt(
    key: Buffer,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun publicEncrypt(
    key: KeyObject,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun publicDecrypt(
    key: RsaPublicKey,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun publicDecrypt(
    key: RsaPrivateKey,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun publicDecrypt(
    key: String,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun publicDecrypt(
    key: Buffer,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun publicDecrypt(
    key: KeyObject,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun privateDecrypt(
    private_key: RsaPrivateKey,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun privateDecrypt(
    private_key: String,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun privateDecrypt(
    private_key: Buffer,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun privateDecrypt(
    private_key: KeyObject,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun privateEncrypt(
    private_key: RsaPrivateKey,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun privateEncrypt(
    private_key: String,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun privateEncrypt(
    private_key: Buffer,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun privateEncrypt(
    private_key: KeyObject,
    buffer: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun getCiphers(): Array<String>

external fun getCurves(): Array<String>

external fun getHashes(): Array<String>

external open class ECDH {
    open fun generateKeys(): Buffer
    open fun generateKeys(
        encoding: String /* "latin1" | "hex" | "hex" | "hex" | "base64" | "base64" | "base64" */,
        format: String /* "compressed" | "uncompressed" | "hybrid" | "compressed" | "uncompressed" | "hybrid" | "compressed" | "uncompressed" | "hybrid" */ = definedExternally
    ): String

    open fun computeSecret(other_public_key: Uint8Array): Buffer
    open fun computeSecret(other_public_key: Uint8ClampedArray): Buffer
    open fun computeSecret(other_public_key: Uint16Array): Buffer
    open fun computeSecret(other_public_key: Uint32Array): Buffer
    open fun computeSecret(other_public_key: Int8Array): Buffer
    open fun computeSecret(other_public_key: Int16Array): Buffer
    open fun computeSecret(other_public_key: Int32Array): Buffer
    open fun computeSecret(other_public_key: Float32Array): Buffer
    open fun computeSecret(other_public_key: Float64Array): Buffer
    open fun computeSecret(other_public_key: DataView): Buffer
    open fun computeSecret(other_public_key: String, input_encoding: String /* "latin1" | "hex" | "base64" */): Buffer
    open fun computeSecret(
        other_public_key: Uint8Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Uint8ClampedArray,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Uint16Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Uint32Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Int8Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Int16Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Int32Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Float32Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: Float64Array,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: DataView,
        output_encoding: String /* "latin1" | "hex" | "base64" */
    ): String

    open fun computeSecret(
        other_public_key: String,
        input_encoding: String /* "latin1" | "hex" | "hex" | "hex" | "base64" | "base64" | "base64" */,
        output_encoding: String /* "latin1" | "hex" | "base64" | "latin1" | "hex" | "base64" | "latin1" | "hex" | "base64" */
    ): String

    open fun getPrivateKey(): Buffer
    open fun getPrivateKey(encoding: String /* "latin1" | "hex" | "base64" */): String
    open fun getPublicKey(): Buffer
    open fun getPublicKey(
        encoding: String /* "latin1" | "hex" | "hex" | "hex" | "base64" | "base64" | "base64" */,
        format: String /* "compressed" | "uncompressed" | "hybrid" | "compressed" | "uncompressed" | "hybrid" | "compressed" | "uncompressed" | "hybrid" */ = definedExternally
    ): String

    open fun setPrivateKey(private_key: Uint8Array)
    open fun setPrivateKey(private_key: Uint8ClampedArray)
    open fun setPrivateKey(private_key: Uint16Array)
    open fun setPrivateKey(private_key: Uint32Array)
    open fun setPrivateKey(private_key: Int8Array)
    open fun setPrivateKey(private_key: Int16Array)
    open fun setPrivateKey(private_key: Int32Array)
    open fun setPrivateKey(private_key: Float32Array)
    open fun setPrivateKey(private_key: Float64Array)
    open fun setPrivateKey(private_key: DataView)
    open fun setPrivateKey(private_key: String, encoding: String /* "latin1" | "hex" | "base64" */)

    companion object {
        fun convertKey(
            key: String,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: Uint8Array,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: Uint8ClampedArray,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: Uint16Array,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: Uint32Array,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: Int8Array,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: Int16Array,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: Int32Array,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: Float32Array,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: Float64Array,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */

        fun convertKey(
            key: DataView,
            curve: String,
            inputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            outputEncoding: String /* "latin1" | "hex" | "base64" */ = definedExternally,
            format: String /* "uncompressed" | "compressed" | "hybrid" */ = definedExternally
        ): dynamic /* Buffer | String */
    }
}

external fun createECDH(curve_name: String): ECDH

external fun timingSafeEqual(
    a: Uint8Array,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external fun timingSafeEqual(
    a: Uint8ClampedArray,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external fun timingSafeEqual(
    a: Uint16Array,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external fun timingSafeEqual(
    a: Uint32Array,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external fun timingSafeEqual(
    a: Int8Array,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external fun timingSafeEqual(
    a: Int16Array,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external fun timingSafeEqual(
    a: Int32Array,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external fun timingSafeEqual(
    a: Float32Array,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external fun timingSafeEqual(
    a: Float64Array,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external fun timingSafeEqual(
    a: DataView,
    b: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Boolean

external var DEFAULT_ENCODING: String

external interface BasePrivateKeyEncodingOptions<T : String> {
    var format: T
    var cipher: String?
        get() = definedExternally
        set(value) = definedExternally
    var passphrase: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface KeyPairKeyObjectResult {
    var publicKey: KeyObject
    var privateKey: KeyObject
}

external interface ECKeyPairKeyObjectOptions {
    var namedCurve: String
}

external interface RSAKeyPairKeyObjectOptions {
    var modulusLength: Number
    var publicExponent: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface DSAKeyPairKeyObjectOptions {
    var modulusLength: Number
    var divisorLength: Number
}

external interface `T$28`<PubF> {
    var type: String /* 'pkcs1' | 'spki' */
    var format: PubF
}

external interface `T$29` {
    var type: String /* 'pkcs1' | 'pkcs8' */
}

external interface RSAKeyPairOptions<PubF : String, PrivF : String> {
    var modulusLength: Number
    var publicExponent: Number?
        get() = definedExternally
        set(value) = definedExternally
    var publicKeyEncoding: `T$28`<Any?>
    var privateKeyEncoding: BasePrivateKeyEncodingOptions<PrivF> /* BasePrivateKeyEncodingOptions<PrivF> & `T$29` */
}

external interface `T$30`<PubF> {
    var type: String /* 'spki' */
    var format: PubF
}

external interface `T$31` {
    var type: String /* 'pkcs8' */
}

external interface DSAKeyPairOptions<PubF : String, PrivF : String> {
    var modulusLength: Number
    var divisorLength: Number
    var publicKeyEncoding: `T$30`<Any?>
    var privateKeyEncoding: BasePrivateKeyEncodingOptions<PrivF> /* BasePrivateKeyEncodingOptions<PrivF> & `T$31` */
}

external interface `T$32` {
    var type: String /* 'sec1' | 'pkcs8' */
}

external interface ECKeyPairOptions<PubF : String, PrivF : String> {
    var namedCurve: String
    var publicKeyEncoding: `T$28`<Any?>
    var privateKeyEncoding: BasePrivateKeyEncodingOptions<PrivF> /* BasePrivateKeyEncodingOptions<PrivF> & `T$32` */
}

external interface KeyPairSyncResult<T1, T2> {
    var publicKey: T1
    var privateKey: T2
}

external fun generateKeyPairSync(
    type: String /* 'rsa' */,
    options: RSAKeyPairOptions<String /* 'pem' */, String /* 'pem' */>
): KeyPairSyncResult<String, String>

external fun generateKeyPairSync(
    type: String /* 'rsa' */,
    options: RSAKeyPairOptions<String /* 'pem' */, String /* 'der' */>
): KeyPairSyncResult<String, Buffer>

external fun generateKeyPairSync(
    type: String /* 'rsa' */,
    options: RSAKeyPairOptions<String /* 'der' */, String /* 'pem' */>
): KeyPairSyncResult<Buffer, String>

external fun generateKeyPairSync(
    type: String /* 'rsa' */,
    options: RSAKeyPairOptions<String /* 'der' */, String /* 'der' */>
): KeyPairSyncResult<Buffer, Buffer>

external fun generateKeyPairSync(type: String /* 'rsa' */, options: RSAKeyPairKeyObjectOptions): KeyPairKeyObjectResult

external fun generateKeyPairSync(
    type: String /* 'dsa' */,
    options: DSAKeyPairOptions<String /* 'pem' */, String /* 'pem' */>
): KeyPairSyncResult<String, String>

external fun generateKeyPairSync(
    type: String /* 'dsa' */,
    options: DSAKeyPairOptions<String /* 'pem' */, String /* 'der' */>
): KeyPairSyncResult<String, Buffer>

external fun generateKeyPairSync(
    type: String /* 'dsa' */,
    options: DSAKeyPairOptions<String /* 'der' */, String /* 'pem' */>
): KeyPairSyncResult<Buffer, String>

external fun generateKeyPairSync(
    type: String /* 'dsa' */,
    options: DSAKeyPairOptions<String /* 'der' */, String /* 'der' */>
): KeyPairSyncResult<Buffer, Buffer>

external fun generateKeyPairSync(type: String /* 'dsa' */, options: DSAKeyPairKeyObjectOptions): KeyPairKeyObjectResult

external fun generateKeyPairSync(
    type: String /* 'ec' */,
    options: ECKeyPairOptions<String /* 'pem' */, String /* 'pem' */>
): KeyPairSyncResult<String, String>

external fun generateKeyPairSync(
    type: String /* 'ec' */,
    options: ECKeyPairOptions<String /* 'pem' */, String /* 'der' */>
): KeyPairSyncResult<String, Buffer>

external fun generateKeyPairSync(
    type: String /* 'ec' */,
    options: ECKeyPairOptions<String /* 'der' */, String /* 'pem' */>
): KeyPairSyncResult<Buffer, String>

external fun generateKeyPairSync(
    type: String /* 'ec' */,
    options: ECKeyPairOptions<String /* 'der' */, String /* 'der' */>
): KeyPairSyncResult<Buffer, Buffer>

external fun generateKeyPairSync(type: String /* 'ec' */, options: ECKeyPairKeyObjectOptions): KeyPairKeyObjectResult

external fun generateKeyPair(
    type: String /* 'rsa' */,
    options: RSAKeyPairOptions<String /* 'pem' */, String /* 'pem' */>,
    callback: (err: Error?, publicKey: String, privateKey: String) -> Unit
)

external fun generateKeyPair(
    type: String /* 'rsa' */,
    options: RSAKeyPairOptions<String /* 'pem' */, String /* 'der' */>,
    callback: (err: Error?, publicKey: String, privateKey: Buffer) -> Unit
)

external fun generateKeyPair(
    type: String /* 'rsa' */,
    options: RSAKeyPairOptions<String /* 'der' */, String /* 'pem' */>,
    callback: (err: Error?, publicKey: Buffer, privateKey: String) -> Unit
)

external fun generateKeyPair(
    type: String /* 'rsa' */,
    options: RSAKeyPairOptions<String /* 'der' */, String /* 'der' */>,
    callback: (err: Error?, publicKey: Buffer, privateKey: Buffer) -> Unit
)

external fun generateKeyPair(
    type: String /* 'rsa' */,
    options: RSAKeyPairKeyObjectOptions,
    callback: (err: Error?, publicKey: KeyObject, privateKey: KeyObject) -> Unit
)

external fun generateKeyPair(
    type: String /* 'dsa' */,
    options: DSAKeyPairOptions<String /* 'pem' */, String /* 'pem' */>,
    callback: (err: Error?, publicKey: String, privateKey: String) -> Unit
)

external fun generateKeyPair(
    type: String /* 'dsa' */,
    options: DSAKeyPairOptions<String /* 'pem' */, String /* 'der' */>,
    callback: (err: Error?, publicKey: String, privateKey: Buffer) -> Unit
)

external fun generateKeyPair(
    type: String /* 'dsa' */,
    options: DSAKeyPairOptions<String /* 'der' */, String /* 'pem' */>,
    callback: (err: Error?, publicKey: Buffer, privateKey: String) -> Unit
)

external fun generateKeyPair(
    type: String /* 'dsa' */,
    options: DSAKeyPairOptions<String /* 'der' */, String /* 'der' */>,
    callback: (err: Error?, publicKey: Buffer, privateKey: Buffer) -> Unit
)

external fun generateKeyPair(
    type: String /* 'dsa' */,
    options: DSAKeyPairKeyObjectOptions,
    callback: (err: Error?, publicKey: KeyObject, privateKey: KeyObject) -> Unit
)

external fun generateKeyPair(
    type: String /* 'ec' */,
    options: ECKeyPairOptions<String /* 'pem' */, String /* 'pem' */>,
    callback: (err: Error?, publicKey: String, privateKey: String) -> Unit
)

external fun generateKeyPair(
    type: String /* 'ec' */,
    options: ECKeyPairOptions<String /* 'pem' */, String /* 'der' */>,
    callback: (err: Error?, publicKey: String, privateKey: Buffer) -> Unit
)

external fun generateKeyPair(
    type: String /* 'ec' */,
    options: ECKeyPairOptions<String /* 'der' */, String /* 'pem' */>,
    callback: (err: Error?, publicKey: Buffer, privateKey: String) -> Unit
)

external fun generateKeyPair(
    type: String /* 'ec' */,
    options: ECKeyPairOptions<String /* 'der' */, String /* 'der' */>,
    callback: (err: Error?, publicKey: Buffer, privateKey: Buffer) -> Unit
)

external fun generateKeyPair(
    type: String /* 'ec' */,
    options: ECKeyPairKeyObjectOptions,
    callback: (err: Error?, publicKey: KeyObject, privateKey: KeyObject) -> Unit
)

external fun sign(
    algorithm: String?,
    data: Uint8Array,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external fun sign(
    algorithm: String?,
    data: Uint8ClampedArray,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external fun sign(
    algorithm: String?,
    data: Uint16Array,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external fun sign(
    algorithm: String?,
    data: Uint32Array,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external fun sign(
    algorithm: String?,
    data: Int8Array,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external fun sign(
    algorithm: String?,
    data: Int16Array,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external fun sign(
    algorithm: String?,
    data: Int32Array,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external fun sign(
    algorithm: String?,
    data: Float32Array,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external fun sign(
    algorithm: String?,
    data: Float64Array,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external fun sign(
    algorithm: String?,
    data: DataView,
    key: dynamic /* String | Buffer | KeyObject | SignPrivateKeyInput */
): Buffer

external interface VerifyKeyWithOptions : KeyObject, SigningOptions

external fun verify(
    algorithm: String?,
    data: Uint8Array,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun verify(
    algorithm: String?,
    data: Uint8ClampedArray,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun verify(
    algorithm: String?,
    data: Uint16Array,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun verify(
    algorithm: String?,
    data: Uint32Array,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun verify(
    algorithm: String?,
    data: Int8Array,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun verify(
    algorithm: String?,
    data: Int16Array,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun verify(
    algorithm: String?,
    data: Int32Array,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun verify(
    algorithm: String?,
    data: Float32Array,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun verify(
    algorithm: String?,
    data: Float64Array,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer

external fun verify(
    algorithm: String?,
    data: DataView,
    key: dynamic /* String | Buffer | KeyObject | VerifyKeyWithOptions */,
    signature: dynamic /* Uint8Array | Uint8ClampedArray | Uint16Array | Uint32Array | Int8Array | Int16Array | Int32Array | Float32Array | Float64Array | DataView */
): Buffer