package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Items
import dev.forcetower.breaker.model.Allocation

data class ClassCompleteDTO(
    val id: Long,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("tipo")
    val type: String,
    @SerializedName("alocacoes")
    val allocations: Items<Allocation>
)