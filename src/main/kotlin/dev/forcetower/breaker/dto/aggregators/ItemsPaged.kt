package dev.forcetower.breaker.dto.aggregators

import com.google.gson.annotations.SerializedName

data class ItemsPaged<T>(
    @SerializedName("proximaPagina")
    val nextPage: Linker?,
    @SerializedName("itens")
    val items: List<T>
)