package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName

internal data class DisciplineDTO(
    val id: Long,
    @SerializedName("codigo")
    val code: String,
    @SerializedName("nome")
    val name: String,
    @SerializedName("ementa")
    val program: String?,
    @SerializedName("cargaHoraria")
    val hours: Int,
    @SerializedName("departamento")
    val department: DisciplineDepartment?
)

internal data class DisciplineDepartment(
    @SerializedName("nome")
    val name: String
)