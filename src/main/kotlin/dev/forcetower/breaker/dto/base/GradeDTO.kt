package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName

data class GradeDTO(
    val ordinal: Int,
    @SerializedName("nomeResumido")
    val name: String,
    @SerializedName("data")
    val date: String?,
    @SerializedName("peso")
    val weight: Int,
    @SerializedName("nota")
    val grade: GradeValueDTO?
)

data class GradeValueDTO(
    @SerializedName("valor")
    val value: Double?
)