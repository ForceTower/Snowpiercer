package dev.forcetower.breaker.model

import com.google.gson.annotations.SerializedName

data class LectureMissed(
    val id: Long,
    @SerializedName("abonada")
    val accredited: Boolean,
    @SerializedName("retroativa")
    val retroactive: Boolean,
    @SerializedName("aula")
    val lecture: Lecture
)