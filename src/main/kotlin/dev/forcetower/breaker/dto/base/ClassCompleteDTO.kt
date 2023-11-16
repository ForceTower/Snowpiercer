package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Items
import dev.forcetower.breaker.dto.aggregators.ItemsPaged
import dev.forcetower.breaker.model.Allocation
import dev.forcetower.breaker.model.Person

internal data class ClassCompleteDTO(
    val id: Long,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("tipo")
    val type: String,
    @SerializedName("alocacoes")
    val allocations: Items<Allocation>?,
    @SerializedName("professores")
    val teachers: Items<PersonWrapperDTO>?,
    @SerializedName("atividadeCurricular")
    val groupDetails: GroupDTO,
    @SerializedName("aulas")
    val lectures: ItemsPaged<LectureDTO>?
)