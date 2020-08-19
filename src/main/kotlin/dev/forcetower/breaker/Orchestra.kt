package dev.forcetower.breaker

import com.google.gson.GsonBuilder
import dev.forcetower.breaker.json.StringJsonPattern
import dev.forcetower.breaker.model.*
import dev.forcetower.breaker.operation.GradesOperation
import dev.forcetower.breaker.operation.LoginOperation
import dev.forcetower.breaker.operation.MessagesOperation
import dev.forcetower.breaker.operation.SemestersOperation
import dev.forcetower.breaker.result.Outcome
import dev.forcetower.breaker.service.TechNoAPI
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Orchestra(
    baseClient: OkHttpClient
) {
    private var credentials: String? = null

    private val gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, StringJsonPattern.STRING_TECH_DESERIALIZER)
        .create()

    private val client = baseClient
        .newBuilder()
//        .addInterceptor(HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BASIC
//        })
        .addInterceptor { chain ->
            val url = chain.request().url
            val request = chain.request()
                .newBuilder()
                .apply {
                    val cred = credentials
                    if (cred != null) header("Authorization", cred)
                    val timestampedUrl = url.newBuilder()
                        .addQueryParameter("_", "${System.currentTimeMillis()}")
                        .build()
                    url(timestampedUrl)
                }
                .build()
            return@addInterceptor chain.proceed(request)
        }.build()

    private val service = Retrofit.Builder()
        .baseUrl("http://academico2.uefs.br/Api/SagresApi/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(TechNoAPI::class.java)

    fun setAuthorization(authorization: Authorization) {
        credentials = Credentials.basic(authorization.username, authorization.password)
    }

    suspend fun login(): Outcome<Person> {
        return LoginOperation().execute(service)
    }

    suspend fun semesters(profileId: Long): Outcome<List<Semester>> {
        return SemestersOperation(profileId).execute(service)
    }

    suspend fun grades(profileId: Long, semesterId: Long): Outcome<List<DisciplineData>> {
        return GradesOperation(profileId, semesterId).execute(service)
    }

    suspend fun messages(profileId: Long, until: String = ""): Outcome<MessagesDataPage> {
        return MessagesOperation(profileId, until).execute(service)
    }

    class Builder {
        private var client: OkHttpClient? = null

        fun client(client: OkHttpClient?): Builder {
            this.client = client
            return this
        }

        private fun createClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .callTimeout(2, TimeUnit.MINUTES)
                .build()
        }

        fun build(): Orchestra {
            val client = this.client ?: createClient()
            return Orchestra(client)
        }
    }
}