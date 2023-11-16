package dev.forcetower.breaker

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.function.BiConsumer
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

internal object Coroutines {
    @JvmOverloads
    @JvmStatic
    fun <R> getContinuation(onFinished: BiConsumer<R?, Throwable?>, dispatcher: CoroutineDispatcher = Dispatchers.IO): Continuation<R> {
        return object : Continuation<R> {
            override val context: CoroutineContext
                get() = dispatcher

            override fun resumeWith(result: Result<R>) {
                onFinished.accept(result.getOrNull(), result.exceptionOrNull())
            }
        }
    }
}