package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Items
import dev.forcetower.breaker.model.DisciplineResult

data class DisciplineCompleteDTO(
    val id: Long,
    @SerializedName("limiteFaltas")
    val missLimit: Int?,
    @SerializedName("atividadeCurricular")
    val activity: DisciplineDTO,
    val classes: Items<ClassCompleteDTO>,
    @SerializedName("avaliacoes")
    val evaluations: Items<EvaluationDTO>?,
    @SerializedName("resultado")
    val result: DisciplineResult?
)