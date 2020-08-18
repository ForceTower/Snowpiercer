package dev.forcetower.breaker.model

data class MessagesDataPage(
    val messages: List<Message>,
    val next: String?
)