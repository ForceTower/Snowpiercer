package dev.forcetower.breaker.model

data class DisciplineData(
    val id: Long,
    val disciplineId: Long,
    val name: String,
    val code: String,
    val program: String?,
    val hours: Int,
    val classes: List<DisciplineClass>,
    val evaluations: List<ClassEvaluation>
)