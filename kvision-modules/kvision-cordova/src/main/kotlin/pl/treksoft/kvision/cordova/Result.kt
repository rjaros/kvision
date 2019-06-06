@file:Suppress(
    "TooManyFunctions", "TooGenericExceptionCaught"
)
/*
 * Source: https://github.com/kittinunf/Result
 *
 * MIT License
 *
 * Copyright (c) 2017 Kittinun Vantasin
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

package pl.treksoft.kvision.cordova

inline fun <reified X> Result<*, *>.getAs() = when (this) {
    is Result.Success -> value as? X
    is Result.Failure -> error as? X
}

inline fun <V : Any> Result<V, *>.success(f: (V) -> Unit) = fold(f, {})

inline fun <E : Exception> Result<*, E>.failure(f: (E) -> Unit) = fold({}, f)

infix fun <V : Any, E : Exception> Result<V, E>.or(fallback: V) = when (this) {
    is Result.Success -> this
    else -> Result.Success(fallback)
}

infix fun <V : Any, E : Exception> Result<V, E>.getOrElse(fallback: V) = when (this) {
    is Result.Success -> value
    else -> fallback
}

inline fun <V : Any, U : Any, E : Exception> Result<V, E>.map(transform: (V) -> U): Result<U, E> = try {
    when (this) {
        is Result.Success -> Result.Success(transform(value))
        is Result.Failure -> Result.Failure(error)
    }
} catch (ex: Exception) {
    @Suppress("UNCHECKED_CAST")
    Result.error(ex as E)
}

inline fun <V : Any, U : Any, E : Exception> Result<V, E>.flatMap(transform: (V) -> Result<U, E>): Result<U, E> = try {
    when (this) {
        is Result.Success -> transform(value)
        is Result.Failure -> Result.Failure(error)
    }
} catch (ex: Exception) {
    @Suppress("UNCHECKED_CAST")
    Result.error(ex as E)
}

fun <V : Any, E : Exception, E2 : Exception> Result<V, E>.mapError(transform: (E) -> E2) = when (this) {
    is Result.Success -> Result.Success(value)
    is Result.Failure -> Result.Failure(transform(error))
}

fun <V : Any, E : Exception, E2 : Exception> Result<V, E>.flatMapError(transform: (E) -> Result<V, E2>) = when (this) {
    is Result.Success -> Result.Success(value)
    is Result.Failure -> transform(error)
}

fun <V : Any, E : Exception> Result<V, E>.any(predicate: (V) -> Boolean): Boolean = try {
    when (this) {
        is Result.Success -> predicate(value)
        is Result.Failure -> false
    }
} catch (ex: Exception) {
    false
}

fun <V : Any, U : Any> Result<V, *>.fanout(other: () -> Result<U, *>): Result<Pair<V, U>, *> =
    flatMap { outer -> other().map { outer to it } }

sealed class Result<out V : Any, out E : Exception> {

    open operator fun component1(): V? = null
    open operator fun component2(): E? = null

    inline fun <X> fold(success: (V) -> X, failure: (E) -> X): X = when (this) {
        is Success -> success(this.value)
        is Failure -> failure(this.error)
    }

    abstract fun get(): V

    class Success<out V : Any>(val value: V) : Result<V, Nothing>() {
        override fun component1(): V? = value

        override fun get(): V = value

        override fun toString() = "[Success: $value]"

        override fun hashCode(): Int = value.hashCode()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return other is Success<*> && value == other.value
        }
    }

    class Failure<out E : Exception>(val error: E) : Result<Nothing, E>() {
        override fun component2(): E? = error

        override fun get() = throw error

        fun getException(): E = error

        override fun toString() = "[Failure: $error]"

        override fun hashCode(): Int = error.hashCode()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return other is Failure<*> && error == other.error
        }
    }

    companion object {
        // Factory methods
        fun <E : Exception> error(ex: E) = Failure(ex)

        fun <V : Any> success(v: V) = Success(v)

        fun <V : Any> of(value: V?, fail: (() -> Exception) = { Exception() }): Result<V, Exception> =
            value?.let { success(it) } ?: error(fail())

        fun <V : Any, E : Exception> of(f: () -> V): Result<V, E> = try {
            success(f())
        } catch (ex: Exception) {
            @Suppress("UNCHECKED_CAST")
            error(ex as E)
        }
    }

}
