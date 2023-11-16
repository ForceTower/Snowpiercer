package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName

internal data class MissedLectureDTO(
    val id: Long,
    @SerializedName("abonada")
    val accredited: Boolean,
    @SerializedName("retroativa")
    val retroactive: Boolean,
    @SerializedName("aula")
    val lecture: LectureDTO
)