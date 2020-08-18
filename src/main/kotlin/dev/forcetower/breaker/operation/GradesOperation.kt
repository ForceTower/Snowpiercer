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
                DisciplineData(
                    it.id,
                    it.activity.id,
                    it.activity.name,
                    it.activity.code,
                    it.activity.program,
                    it.activity.hours,
                    it.classes.items.map { clazz ->
                        DisciplineClass(
                            clazz.type,
                            clazz.allocations.items
                        )
                    },
                    it.evaluations?.items?.map { evaluation ->
                        ClassEvaluation(
                            evaluation.name,
                            evaluation.grades?.items?.map { grade ->
                                ClassGrade(
                                    grade.ordinal,
                                    grade.name,
                                    grade.date,
                                    grade.weight,
                                    grade.grade?.value
                                )
                            } ?: emptyList()
                        )
                    } ?: emptyList()
                )
            }
            Outcome.success(result)
        } catch (error: HttpException) {
            error.printStackTrace()
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            error.printStackTrace()
            Outcome.error(error, 500)
        }
    }
}