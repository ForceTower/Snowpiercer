package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Items
import dev.forcetower.breaker.model.Allocation
import dev.forcetower.breaker.model.Person

data class ClassCompleteDTO(
    val id: Long,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("tipo")
    val type: String,
    @SerializedName("alocacoes")
    val allocations: Items<Allocation>,
    @SerializedName("professores")
    val teachers: Items<Person>,
    @SerializedName("atividadeCurricular")
    val groupDetails: GroupDTO
)