package dev.forcetower.breaker.model

data class ClassEvaluation(
    val id: String,
    val name: String?,
    val grades: List<ClassGrade>
)