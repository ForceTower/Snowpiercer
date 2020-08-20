package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.Lecture
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import retrofit2.HttpException

class LectureOperation(
    private val classId: Long,
    private val limit: Int = 0,
    private val offset: Int = 0
) : Operation<List<Lecture>> {
    override suspend fun execute(service: TechNoAPI): Outcome<List<Lecture>> {
        return try {
            val response = service.lectures(classId, limit, offset)
            val lectures = response.items.map { Lecture.fromDTO(it) }
            Outcome.success(lectures)
        } catch (error: HttpException) {
            error.printStackTrace()
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            error.printStackTrace()
            Outcome.error(error, 500)
        }
    }
}