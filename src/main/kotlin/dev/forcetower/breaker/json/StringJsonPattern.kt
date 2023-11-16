package dev.forcetower.breaker.json

import com.google.gson.JsonDeserializer
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

internal object StringJsonPattern {
    @JvmStatic
    val STRING_TECH_DESERIALIZER: JsonDeserializer<String?> = JsonDeserializer { json, _, _ ->
        val jsonPrimitive = json.asJsonPrimitive
        return@JsonDeserializer if (jsonPrimitive.isString) {
            val string = json.asString
            string.trim()
        } else {
            null
        }
    }

    @JvmStatic
    val ZONED_DATE_TIME_DESERIALIZER: JsonDeserializer<ZonedDateTime> = JsonDeserializer { json, _, _ ->
        val jsonPrimitive = json.asJsonPrimitive
        return@JsonDeserializer try {
            if (jsonPrimitive.isString) {
                ZonedDateTime.parse(jsonPrimitive.asString.trim(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            } else {
                throw IllegalStateException("Not a valid zoned date time. ${jsonPrimitive.asString}")
            }
        } catch (error: Throwable) {
            error.printStackTrace()
            throw IllegalStateException("Not a valid zoned date time. $json")
        }
    }
}