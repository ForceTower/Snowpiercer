package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.Authorization
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import okhttp3.Credentials

internal interface Operation<T> {
    fun createAuth(authorization: Authorization) = Credentials.basic(authorization.username, authorization.password)
    suspend fun execute(service: TechNoAPI, authorization: Authorization): Outcome<T>
}