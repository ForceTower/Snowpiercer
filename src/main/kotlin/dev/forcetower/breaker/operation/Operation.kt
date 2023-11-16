package dev.forcetower.breaker.operation

import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI

internal interface Operation<T> {
    suspend fun execute(service: TechNoAPI): Outcome<T>
}