package dev.forcetower.breaker.model

import com.google.gson.annotations.SerializedName

data class DisciplineResult(
    @SerializedName("media")
    val mean: Double?,
    @SerializedName("totalFaltas")
    val missedClasses: Int,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("aprovado")
    val approved: Boolean,
    @SerializedName("emRevisao")
    val underRevision: Boolean
)