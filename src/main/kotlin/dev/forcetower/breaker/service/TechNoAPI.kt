package dev.forcetower.breaker.service

import dev.forcetower.breaker.dto.aggregators.Items
import dev.forcetower.breaker.dto.base.DisciplineCompleteDTO
import dev.forcetower.breaker.dto.base.SemesterCompleteDTO
import dev.forcetower.breaker.model.Person
import dev.forcetower.breaker.model.Semester
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TechNoAPI {
    @GET("eu")
    suspend fun me(): Person

    @GET("diario/periodos-letivos")
    suspend fun semesters(
        @Query("idPessoa") id: Long,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "itens(id,codigo,descricao)",
        @Query("quantidade") amount: Int = 0
    ): Items<Semester>

    @GET("diario/periodos-letivos/{semesterId}")
    suspend fun grades(
        @Path("semesterId") semesterId: Long,
        @Query("idPessoa") id: Long,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "id,codigo,descricao,turmas(itens(id,limiteFaltas,resultado(-%24link),classes(itens(id,descricao,tipo,alocacoes(itens(espacoFisico,horario)))),atividadeCurricular(id,nome,codigo,ementa,cargaHoraria),ultimaAula(data),proximaAula(data),avaliacoes(itens(nome,nomeResumido,nota,avaliacoes(itens(ordinal,nomeResumido,data,peso,nota(valor))))),periodoLetivo(codigo)))",
        @Query("embutir", encoded = true) append: String = "turmas(itens(resultado,classes(itens(alocacoes(itens(espacoFisico,horario)))),atividadeCurricular,ultimaAula,proximaAula,avaliacoes(itens(avaliacoes(itens(nota)))),periodoLetivo(codigo)))",
        @Query("quantidade") amount: Int = 0
    ): SemesterCompleteDTO

    @GET("diario/turmas")
    suspend fun classes(
        @Query("idPessoa") profileId: Long,
        @Query("idPeriodoLetivo") semesterId: Long,
        @Query("perfil") profile: Int = 1,
        @Query("embutir", encoded = true) append: String = "itens(atividadeCurricular,classes(itens(alocacoes(itens(espacoFisico,horario)))))",
        @Query("campos", encoded = true) fields: String = "itens(id,atividadeCurricular(id,codigo,nome,ementa,cargaHoraria),classes(itens(id,tipo,descricao,alocacoes(itens(espacoFisico,horario)))))",
        @Query("quantidade") amount: Int = 0
    ): Items<DisciplineCompleteDTO>

    @GET("diario/eventos-academicos")
    suspend fun events(
        @Query("idPessoa") id: Long,
        @Query("desde", encoded = true) since: String, //2020-08-01T00:00:00.000Z
        @Query("ate", encoded = true) until: String //2020-08-31T00:00:00.000Z
    ): Any

    @GET("diario/debitos")
    suspend fun debts(
        @Query("idAluno") id: Long
    ): Any
}