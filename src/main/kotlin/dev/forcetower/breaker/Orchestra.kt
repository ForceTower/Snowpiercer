package dev.forcetower.breaker

import com.google.gson.GsonBuilder
import dev.forcetower.breaker.json.StringJsonPattern
import dev.forcetower.breaker.model.*
import dev.forcetower.breaker.operation.*
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.ZonedDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class Orchestra(
    baseClient: OkHttpClient,
    agent: String
) {
    private var authorization: Authorization? = null
    private var credentials: String? = null

    private val gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, StringJsonPattern.STRING_TECH_DESERIALIZER)
        .registerTypeAdapter(ZonedDateTime::class.java, StringJsonPattern.ZONED_DATE_TIME_DESERIALIZER)
        .create()

    private val client = baseClient
        .newBuilder()
        .addInterceptor { chain ->
            val url = chain.request().url
            val request = chain.request()
                .newBuilder()
                .apply {
                    val timestampedUrl = url.newBuilder()
                        .addQueryParameter("_", "${System.currentTimeMillis()}")
                        .build()
                    url(timestampedUrl)
                    addHeader("User-Agent", agent)
                    addHeader("X-Requested-With", "com.tecnotrends.sagres.mobile")
                    addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                    addHeader("Accept-Encoding", "gzip, deflate")
                    addHeader("Accept-Language", "en-US,en;q=0.9")
                }
                .build()
            return@addInterceptor chain.proceed(request)
        }.build()

    private val service = Retrofit.Builder()
        .baseUrl("http://academico.uefs.br/Api/SagresApi/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(TechNoAPI::class.java)

    fun setAuthorization(authorization: Authorization) {
        this.authorization = authorization
        credentials = Credentials.basic(authorization.username, authorization.password)
    }

    private fun requireAuthorization(): Authorization {
        return authorization ?: throw IllegalStateException("You must set an authorization to use this resource")
    }

    suspend fun login(authorization: Authorization? = null): Outcome<Person> {
        return LoginOperation().execute(service, authorization ?: this.requireAuthorization())
    }

    suspend fun semesters(profileId: Long, authorization: Authorization? = null): Outcome<List<Semester>> {
        return SemestersOperation(profileId).execute(service, authorization ?: this.requireAuthorization())
    }

    suspend fun allSemestersInformation(profileId: Long, authorization: Authorization? = null): Outcome<List<SemesterInformation>> {
        return AllSemestersInfo(profileId).execute(service, authorization ?: this.requireAuthorization())
    }

    suspend fun grades(profileId: Long, semesterId: Long, authorization: Authorization? = null): Outcome<List<DisciplineData>> {
        return GradesOperation(profileId, semesterId).execute(service, authorization ?: this.requireAuthorization())
    }

    suspend fun messages(profileId: Long, until: String = "", amount: Int = 10, authorization: Authorization? = null): Outcome<MessagesDataPage> {
        return MessagesOperation(profileId, until, amount).execute(service, authorization ?: this.requireAuthorization())
    }

    suspend fun lectures(classId: Long, limit: Int, offset: Int, authorization: Authorization? = null): Outcome<List<Lecture>> {
        return LectureOperation(classId, limit, offset).execute(service, authorization ?: this.requireAuthorization())
    }

    suspend fun absences(profileId: Long, classId: Long, limit: Int, offset: Int, authorization: Authorization? = null): Outcome<List<LectureMissed>> {
        return AbsenceOperation(profileId, classId, limit, offset).execute(service, authorization ?: this.requireAuthorization())
    }

    suspend fun classDetails(profileId: Long, classId: Long, authorization: Authorization? = null): Outcome<DisciplineData> {
        return ClassDetailsOperation(classId, profileId).execute(service, authorization ?: this.requireAuthorization())
    }

    class Builder {
        private var client: OkHttpClient? = null
        private var agent: String? = null

        fun client(client: OkHttpClient?): Builder {
            this.client = client
            return this
        }

        fun userAgent(agent: String): Builder {
            this.agent = agent
            return this
        }

        private fun createClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .build()
        }

        fun build(): Orchestra {
            val client = this.client ?: createClient()
            val agent = this.agent ?: "Mozilla/5.0 (Linux; Android 10; MI 9 Build/QKQ1.190825.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/84.0.4147.125 Mobile Safari/537.36"
            return Orchestra(client, agent)
        }
    }
}