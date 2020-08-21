package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.Lecture
import dev.forcetower.breaker.model.LectureMissed
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import retrofit2.HttpException

class AbsenceOperation(
    private val profileId: Long,
    private val classId: Long,
    private val limit: Int = 0,
    private val offset: Int = 0
) : Operation<List<LectureMissed>> {
    override suspend fun execute(service: TechNoAPI): Outcome<List<LectureMissed>> {
        return try {
            val response = service.absences(classId, profileId, limit, offset)
            val absences = response.items.map {
                LectureMissed(
                    it.id,
                    it.accredited,
                    it.retroactive,
                    Lecture.fromDTO(it.lecture)
                )
            }
            Outcome.success(absences)
        } catch (error: HttpException) {
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            error.printStackTrace()
            Outcome.error(error, 500)
        }
    }
}