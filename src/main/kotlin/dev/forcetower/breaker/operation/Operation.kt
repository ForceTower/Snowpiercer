package dev.forcetower.breaker.operation

import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI

interface Operation<T> {
    suspend fun execute(service: TechNoAPI): Outcome<T>
}