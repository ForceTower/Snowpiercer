package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Items

internal data class EvaluationDTO(
    val id: String,
    @SerializedName("nome")
    val name: String?,
    @SerializedName("avaliacoes")
    val grades: Items<GradeDTO>?
)