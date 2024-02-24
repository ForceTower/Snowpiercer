package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.DisciplineData
import dev.forcetower.breaker.model.Lecture
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import retrofit2.HttpException

internal class ClassDetailsOperation(
    private val classId: Long,
    private val profileId: Long
) : Operation<DisciplineData> {
    override suspend fun execute(service: TechNoAPI): Outcome<DisciplineData> {
        return try {
            val response = service.classDetails(classId, profileId)
            Outcome.success(DisciplineData.createFromDTO(response))
        } catch (error: HttpException) {
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            Outcome.error(error, 500)
        }
    }
}