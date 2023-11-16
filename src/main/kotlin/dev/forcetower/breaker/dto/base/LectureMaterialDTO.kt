package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Linker

internal data class LectureMaterialDTO(
    val id: Long,
    @SerializedName("descricao")
    val description: String,
    val url: Linker
)