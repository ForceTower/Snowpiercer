package dev.forcetower.breaker

import dev.forcetower.breaker.model.*
import dev.forcetower.breaker.result.Outcome
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

@OptIn(DelicateCoroutinesApi::class)
class JOrchestra constructor(private val instance: Orchestra) {
    fun setAuthorization(authorization: Authorization) {
        instance.setAuthorization(authorization)
    }

    fun login(): CompletableFuture<Outcome<Person>> {
        return GlobalScope.future { instance.login() }
    }

    fun semesters(profileId: Long): CompletableFuture<Outcome<List<Semester>>> {
        return GlobalScope.future { instance.semesters(profileId) }
    }

    fun allSemestersInformation(profileId: Long): CompletableFuture<Outcome<List<SemesterInformation>>> {
        return GlobalScope.future { instance.allSemestersInformation(profileId) }
    }

    fun grades(profileId: Long, semesterId: Long): CompletableFuture<Outcome<List<DisciplineData>>> {
        return GlobalScope.future { instance.grades(profileId, semesterId) }
    }

    fun messages(profileId: Long, until: String = ""): CompletableFuture<Outcome<MessagesDataPage>> {
        return GlobalScope.future { instance.messages(profileId, until) }
    }

    fun lectures(classId: Long, limit: Int, offset: Int): CompletableFuture<Outcome<List<Lecture>>> {
        return GlobalScope.future { instance.lectures(classId, limit, offset) }
    }

    fun absences(profileId: Long, classId: Long, limit: Int, offset: Int): CompletableFuture<Outcome<List<LectureMissed>>> {
        return GlobalScope.future { instance.absences(profileId, classId, limit, offset) }
    }
}