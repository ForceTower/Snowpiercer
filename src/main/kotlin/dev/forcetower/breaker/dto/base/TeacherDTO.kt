package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName

data class TeacherDTO(
    @SerializedName("pessoa")
    val person: NamedObject
)