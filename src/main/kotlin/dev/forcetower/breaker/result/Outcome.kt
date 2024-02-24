package dev.forcetower.breaker.result

sealed class Outcome<out T> {
    data class Success<T>(val value: T) : Outcome<T>()
    data class Error<T>(val error: Throwable, val code: Int): Outcome<T>()

    val isSuccess: Boolean
        get() = this is Success

    fun asSuccess() = this as Success<out T>
    fun asError() = this as Error<out T>
    fun success() = this as? Success<out T>
    fun error() = this as? Error<out T>

    companion object {
        fun <T> success(value: T): Success<T> {
            return Success(value)
        }

        fun <T> error(error: Throwable, code: Int = 500): Error<T> {
            return Error(error, code)
        }
    }
}