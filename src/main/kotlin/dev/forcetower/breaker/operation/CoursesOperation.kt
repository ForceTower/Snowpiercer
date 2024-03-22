package dev.forcetower.breaker.operation

import dev.forcetower.breaker.dto.aggregators.Items
import dev.forcetower.breaker.dto.base.CourseDataDTO
import dev.forcetower.breaker.model.Authorization
import dev.forcetower.breaker.model.Course
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI

internal class CoursesOperation(
    private val personId: Long
) : Operation<Course> {
    override suspend fun execute(service: TechNoAPI, authorization: Authorization): Outcome<Course> {
        val response = service.course(createAuth(authorization), personId)

        val item = response.items.firstOrNull() ?: return Outcome.error(IllegalStateException("Nothing found"))
        return Outcome.success(
            Course(
                item.id,
                item.name,
                item.resumed
            )
        )
    }
}