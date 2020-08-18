package dev.forcetower.breaker.dto.aggregators

import com.google.gson.annotations.SerializedName

data class Items<T>(
    @SerializedName("itens")
    val items: List<T>
)