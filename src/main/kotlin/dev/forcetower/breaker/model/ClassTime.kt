package dev.forcetower.breaker.model

import com.google.gson.annotations.SerializedName

data class ClassTime(
    val id: Long,
    @SerializedName("dia")
    val day: Int,
    @SerializedName("inicio")
    val start: String,
    @SerializedName("fim")
    val end: String
)