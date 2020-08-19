package dev.forcetower.breaker.model

data class DisciplineClass(
    val groupName: String,
    val type: String,
    val teacher: Person,
    val hours: Int,
    val program: String?,
    val allocations: List<Allocation>
)