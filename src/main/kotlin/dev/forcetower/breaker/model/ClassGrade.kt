package dev.forcetower.breaker.model

data class ClassGrade(
    val id: String,
    val ordinal: Int,
    val name: String,
    val nameShort: String,
    val date: String?,
    val weight: Int,
    val value: Double?
) {
    fun hasGrade(): Boolean {
        return value != null
    }
}