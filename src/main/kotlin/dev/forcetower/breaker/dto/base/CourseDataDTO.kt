package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName

data class CourseDataDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("nome")
    val name: String,
    @SerializedName("nomeResumido")
    val resumed: String
)