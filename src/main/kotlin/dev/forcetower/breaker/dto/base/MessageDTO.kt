package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.dto.aggregators.Items

data class MessageDTO(
    val id: Long,
    @SerializedName("descricao")
    val message: String,
    @SerializedName("perfilRemetente")
    val profileType: Int,
    @SerializedName("timeStamp")
    val timestamp: String,
    @SerializedName("remetente")
    val sender: Sender,
    @SerializedName("escopos")
    val scopes: Items<MessageScopeDTO>
)

data class Sender(
    @SerializedName("nome")
    val name: String
)

data class MessageScopeDTO(
    val id: Long,
    @SerializedName("tipo")
    val type: Int,
    @SerializedName("classe")
    val clazz: MessageClassResumed?
)

data class MessageClassResumed(
    val id: Long,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("tipo")
    val type: String,
    @SerializedName("atividadeCurricular")
    val discipline: DisciplineDTO
)