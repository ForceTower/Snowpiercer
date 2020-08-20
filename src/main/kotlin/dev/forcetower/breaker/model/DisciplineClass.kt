package dev.forcetower.breaker.model

data class DisciplineClass(
    val id: Long,
    val groupName: String,
    val type: String,
    val teacher: Person?,
    val hours: Int,
    val program: String?,
    val allocations: List<Allocation>,
    val lectures: List<Lecture>
)