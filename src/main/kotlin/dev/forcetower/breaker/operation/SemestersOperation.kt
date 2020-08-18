package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.Semester
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import retrofit2.HttpException

class SemestersOperation(private val profileId: Long) : Operation<List<Semester>> {
    override suspend fun execute(service: TechNoAPI): Outcome<List<Semester>> {
        return try {
            val semesters = service.semesters(profileId)
            val mapped = semesters.items
            Outcome.success(mapped)
        } catch (error: HttpException) {
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            Outcome.error(error, 500)
        }
    }
}