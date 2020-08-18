package dev.forcetower.breaker.model

import com.google.gson.annotations.SerializedName

data class Discipline(
    val id: Long,
    @SerializedName("codigo")
    val code: String,
    @SerializedName("nome")
    val name: String,
    @SerializedName("ementa")
    val program: String?,
    @SerializedName("cargaHoraria")
    val hours: Int
)