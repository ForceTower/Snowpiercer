package dev.forcetower.breaker.operation

import dev.forcetower.breaker.model.Message
import dev.forcetower.breaker.model.MessageDiscipline
import dev.forcetower.breaker.model.MessagesDataPage
import dev.forcetower.breaker.model.Semester
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import retrofit2.HttpException

internal class MessagesOperation(
    private val profileId: Long,
    private val until: String = "",
    private val amount: Int = 10
) : Operation<MessagesDataPage> {
    override suspend fun execute(service: TechNoAPI): Outcome<MessagesDataPage> {
        return try {
            val messages = service.messages(profileId, until, amount)
            val mapped = messages.items.map {
                val clazz = it.scopes.items.firstOrNull()?.clazz
                val discipline = clazz?.let { element ->
                    MessageDiscipline(
                        element.discipline.id,
                        element.id,
                        element.discipline.code,
                        element.discipline.name,
                        element.type
                    )
                }
                Message(
                    it.id,
                    it.message,
                    it.sender.name,
                    it.timestamp,
                    it.profileType,
                    discipline
                )
            }
            val regex = Regex("ate=(\\d+-\\d+)")
            val next = messages.nextPage?.link?.href?.let { ref ->
                val groups = regex.find(ref)?.groups
                val size = groups?.size ?: 0
                if (size > 1) {
                    groups?.get(1)?.value
                } else {
                    null
                }
            }
            Outcome.success(MessagesDataPage(mapped, next))
        } catch (error: HttpException) {
            Outcome.error(error, error.code())
        } catch (error: Throwable) {
            Outcome.error(error, 500)
        }
    }
}