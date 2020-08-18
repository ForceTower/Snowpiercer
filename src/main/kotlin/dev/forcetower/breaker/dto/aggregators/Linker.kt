package dev.forcetower.breaker.dto.aggregators

import com.google.gson.annotations.SerializedName

data class Linker(
    @SerializedName("\$link")
    val link: Link
)

data class Link (
    val href: String
)

fun Linker.idLong(): Long {
    return link.href.split("/").last().toLong()
}