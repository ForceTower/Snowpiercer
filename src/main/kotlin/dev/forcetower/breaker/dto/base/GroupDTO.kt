package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName

internal data class GroupDTO(
    val id: Long,
    @SerializedName("cargaHoraria")
    val hours: Int,
    @SerializedName("ementa")
    val program: String?
)