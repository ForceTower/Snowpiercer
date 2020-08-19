package dev.forcetower.breaker.model


data class Discipline(
    val id: Long,
    val code: String,
    val name: String,
    val program: String?,
    val hours: Int,
    val department: String
)