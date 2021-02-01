package dev.forcetower.breaker.model

import com.google.gson.annotations.SerializedName

data class Semester(
    val id: Long,
    @SerializedName("codigo")
    val code: String,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("inicio")
    val start: String,
    @SerializedName("fim")
    val end: String
)