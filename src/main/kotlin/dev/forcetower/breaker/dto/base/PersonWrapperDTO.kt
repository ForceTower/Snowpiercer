package dev.forcetower.breaker.dto.base

import com.google.gson.annotations.SerializedName
import dev.forcetower.breaker.model.Person

internal data class PersonWrapperDTO(
    @SerializedName("pessoa")
    val person: Person
)