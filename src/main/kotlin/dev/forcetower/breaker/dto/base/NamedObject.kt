package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName

data class NamedObject(
    @SerializedName("nome")
    val name: String
)