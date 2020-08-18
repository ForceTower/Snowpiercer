package dev.forcetower.breaker.json

import com.google.gson.JsonDeserializer

object StringJsonPattern {
    @JvmStatic
    val STRING_TECH_DESERIALIZER: JsonDeserializer<String?> = JsonDeserializer { json, _, _ ->
        val jsonPrimitive = json.asJsonPrimitive
        return@JsonDeserializer try {
            if (jsonPrimitive.isString) {
                val string = json.asString
                string.trim()
            } else {
                println("Error!!!!!!!!!!!!!!!!!!!")
                null
            }
        } catch (error: Throwable) {
            error.printStackTrace()
            null
        }
    }
}