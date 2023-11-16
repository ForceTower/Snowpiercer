package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName

internal data class TeacherDTO(
    @SerializedName("pessoa")
    val person: NamedObject
)