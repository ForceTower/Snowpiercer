package dev.forcetower.breaker.model

data class Message(
    val content: String,
    val sender: String,
    val timestamp: String,
    val senderType: Int
)