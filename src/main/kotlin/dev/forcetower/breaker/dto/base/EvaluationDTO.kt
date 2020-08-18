package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Items

data class EvaluationDTO(
    @SerializedName("nome")
    val name: String,
    @SerializedName("avaliacoes")
    val grades: Items<GradeDTO>?
)