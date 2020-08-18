package dev.forcetower.breaker.model

import com.google.gson.annotations.SerializedName

data class ClassSpace(
    val id: Long,
    @SerializedName("tipo")
    val type: String?,  // D.A. DE ECOMP ????????????
    @SerializedName("pavilhao")
    val campus: String, // UEFS
    @SerializedName("numero")
    val location: String, // MT58
    @SerializedName("localizacao")
    val modulo: String  // Modulo 5
)