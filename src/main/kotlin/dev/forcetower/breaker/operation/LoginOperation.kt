package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.Person
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import retrofit2.HttpException

internal class LoginOperation : Operation<Person> {
    override suspend fun execute(service: TechNoAPI): Outcome<Person> {
        return try {
            val me = service.me()
            Outcome.success(me)
        } catch (error: HttpException) {
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            Outcome.error(error, 500)
        }
    }
}