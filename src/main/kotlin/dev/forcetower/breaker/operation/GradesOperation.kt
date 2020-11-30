package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.*
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import retrofit2.HttpException

class GradesOperation(
    private val profileId: Long,
    private val semesterId: Long,
) : Operation<List<DisciplineData>> {
    override suspend fun execute(service: TechNoAPI): Outcome<List<DisciplineData>> {
        return try {
            val classes = service.grades(semesterId, profileId)
            val result = classes.disciplines.items.map {
                DisciplineData.createFromDTO(it)
            }
            Outcome.success(result)
        } catch (error: HttpException) {
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            Outcome.error(error, 500)
        }
    }
}