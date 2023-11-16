package dev.forcetower.breaker.model

import dev.forcetower.breaker.dto.base.DisciplineCompleteDTO

data class DisciplineData(
    val id: Long,
    val disciplineId: Long,
    val name: String,
    val code: String,
    val program: String?,
    val hours: Int,
    val department: String?,
    val classes: List<DisciplineClass>,
    val evaluations: List<ClassEvaluation>,
    val result: DisciplineResult?
) {
    companion object {
        internal fun createFromDTO(it: DisciplineCompleteDTO): DisciplineData {
            return DisciplineData(
                it.id,
                it.activity.id,
                it.activity.name,
                it.activity.code,
                it.activity.program,
                it.activity.hours,
                it.activity.department?.name,
                it.classes.items.map { clazz ->
                    DisciplineClass(
                        clazz.id,
                        clazz.description,
                        clazz.type,
                        clazz.teachers?.items?.firstOrNull()?.person,
                        clazz.groupDetails.hours,
                        clazz.groupDetails.program,
                        clazz.allocations?.items ?: emptyList(),
                        clazz.lectures?.items?.map { lecture ->
                            Lecture.fromDTO(lecture)
                        } ?: emptyList()
                    )
                },
                it.evaluations?.items?.map { evaluation ->
                    ClassEvaluation(
                        evaluation.name,
                        evaluation.grades?.items?.map { grade ->
                            ClassGrade(
                                grade.ordinal,
                                grade.name,
                                grade.nameShort,
                                grade.date,
                                grade.weight,
                                grade.grade?.value
                            )
                        } ?: emptyList()
                    )
                } ?: emptyList(),
                it.result
            )
        }
    }
}