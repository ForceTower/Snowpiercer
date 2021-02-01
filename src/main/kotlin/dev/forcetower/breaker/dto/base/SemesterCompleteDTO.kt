package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Items
import java.time.ZonedDateTime

data class SemesterCompleteDTO(
    val id: Long,
    @SerializedName("codigo")
    val code: String,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("inicio")
    val start: String,
    @SerializedName("fim")
    val end: String,
    @SerializedName("turmas")
    val disciplines: Items<DisciplineCompleteDTO>
)