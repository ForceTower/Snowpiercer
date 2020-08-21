package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Items

data class LectureDTO(
    val ordinal: Int,
    @SerializedName("situacao")
    val situation: Int,
    @SerializedName("data")
    val date: String,
    @SerializedName("assunto")
    val subject: String,
    @SerializedName("materiaisApoio")
    val materials: Items<LectureMaterialDTO>?
)