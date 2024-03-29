package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.*
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import retrofit2.HttpException

internal class AllSemestersInfo(
    private val profileId: Long,
) : Operation<List<SemesterInformation>> {
    override suspend fun execute(service: TechNoAPI, authorization: Authorization): Outcome<List<SemesterInformation>> {
        return try {
            val response = service.gradesForTesting(createAuth(authorization), profileId)
            val result = response.items.map { semester ->
                SemesterInformation(
                    Semester(semester.id, semester.code, semester.description, semester.start, semester.end),
                    semester.disciplines.items.map {
                        DisciplineData.createFromDTO(it)
                    }
                )
            }
            Outcome.success(result)
        } catch (error: HttpException) {
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            Outcome.error(error, 500)
        }
    }
}