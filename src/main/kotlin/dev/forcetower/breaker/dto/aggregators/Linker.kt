package dev.forcetower.breaker.dto.aggregators

import com.google.gson.annotations.SerializedName

internal data class Linker(
    @SerializedName("\$link")
    val link: Link
)

internal data class Link (
    val href: String
)

internal fun Linker.idLong(): Long {
    return link.href.split("/").last().toLong()
}