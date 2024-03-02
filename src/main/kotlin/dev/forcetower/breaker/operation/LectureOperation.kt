package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.Authorization
import dev.forcetower.breaker.model.Lecture
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import retrofit2.HttpException

internal class LectureOperation(
    private val classId: Long,
    private val limit: Int = 0,
    private val offset: Int = 0
) : Operation<List<Lecture>> {
    override suspend fun execute(service: TechNoAPI, authorization: Authorization): Outcome<List<Lecture>> {
        return try {
            val response = service.lectures(createAuth(authorization), classId, limit, offset)
            val lectures = response.items.map { Lecture.fromDTO(it) }
            Outcome.success(lectures)
        } catch (error: HttpException) {
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            Outcome.error(error, 500)
        }
    }
}