package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.Authorization
import dev.forcetower.breaker.model.Person
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import okhttp3.Credentials
import retrofit2.HttpException

internal class LoginOperation : Operation<Person> {
    override suspend fun execute(service: TechNoAPI, authorization: Authorization): Outcome<Person> {
        return try {
            val me = service.me(createAuth(authorization))
            Outcome.success(me)
        } catch (error: HttpException) {
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            Outcome.error(error, 500)
        }
    }
}