package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName

data class MessageDTO(
    @SerializedName("descricao")
    val message: String,
    @SerializedName("perfilRemetente")
    val profileType: Int,
    @SerializedName("timeStamp")
    val timestamp: String,
    @SerializedName("remetente")
    val sender: Sender
)

data class Sender(
    @SerializedName("nome")
    val name: String
)