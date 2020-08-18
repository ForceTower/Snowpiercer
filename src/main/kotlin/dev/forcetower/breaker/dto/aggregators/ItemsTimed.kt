package dev.forcetower.breaker.dto.aggregators

import com.google.gson.annotations.SerializedName

data class ItemsTimed<T>(
    @SerializedName("maisAntigos")
    val nextPage: Linker?,
    @SerializedName("maisRecentes")
    val previousPage: Linker?,
    @SerializedName("itens")
    val items: List<T>
)