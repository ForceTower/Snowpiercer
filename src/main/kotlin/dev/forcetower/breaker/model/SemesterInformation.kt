package dev.forcetower.breaker.model

data class SemesterInformation(
    val semester: Semester,
    val disciplines: List<DisciplineData>
)