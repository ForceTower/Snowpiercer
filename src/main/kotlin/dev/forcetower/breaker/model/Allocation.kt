package dev.forcetower.breaker.model

import com.google.gson.annotations.SerializedName

data class Allocation(
    @SerializedName("horario")
    val time: ClassTime?,
    @SerializedName("espacoFisico")
    val space: ClassSpace?
) {
    override fun toString(): String {
        return "Day: ${time?.day}. From ${time?.start} until ${time?.end} at ${space?.modulo}, ${space?.location} - ${space?.campus}"
    }
}