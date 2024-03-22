package dev.forcetower.breaker.service

import dev.forcetower.breaker.dto.aggregators.Items
import dev.forcetower.breaker.dto.aggregators.ItemsPaged
import dev.forcetower.breaker.dto.aggregators.ItemsTimed
import dev.forcetower.breaker.dto.base.*
import dev.forcetower.breaker.model.Authorization
import dev.forcetower.breaker.model.Person
import dev.forcetower.breaker.model.Semester
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

internal interface TechNoAPI {
    @GET("eu")
    suspend fun me(@Header("Authorization") auth: String): Person

    @GET("diario/periodos-letivos")
    suspend fun semesters(
        @Header("Authorization") auth: String,
        @Query("idPessoa") id: Long,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "itens(id,codigo,descricao,inicio,fim)",
        @Query("quantidade") amount: Int = 0
    ): Items<Semester>

    @GET("diario/periodos-letivos/{semesterId}")
    suspend fun grades(
        @Header("Authorization") auth: String,
        @Path("semesterId") semesterId: Long,
        @Query("idPessoa") id: Long,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "id,codigo,descricao,turmas(itens(id,limiteFaltas,resultado(-%24link),classes(itens(atividadeCurricular(id,ementa,cargaHoraria),id,descricao,tipo,professores(itens(pessoa(id,nome,email,tipoPessoa))),alocacoes(itens(espacoFisico,horario)))),atividadeCurricular(id,nome,codigo,ementa,cargaHoraria,departamento(nome)),avaliacoes(itens(id,nome,nomeResumido,nota,avaliacoes(itens(id,nome,ordinal,nomeResumido,data,peso,nota(valor))))),periodoLetivo(codigo)))",
        @Query("embutir", encoded = true) append: String = "turmas(itens(resultado,classes(itens(atividadeCurricular,professores(itens(pessoa)),alocacoes(itens(espacoFisico,horario)))),atividadeCurricular(departamento(nome)),avaliacoes(itens(avaliacoes(itens(nota)))),periodoLetivo(codigo)))"
    ): SemesterCompleteDTO

    @GET("diario/periodos-letivos")
    suspend fun gradesForTesting(
        @Header("Authorization") auth: String,
        @Query("idPessoa") id: Long,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "itens(id,codigo,descricao,inicio,fim,turmas(itens(id,limiteFaltas,resultado(-%24link),classes(itens(atividadeCurricular(id,ementa,cargaHoraria),id,descricao,tipo,professores(itens(pessoa(id,nome,email,tipoPessoa))))),atividadeCurricular(id,nome,codigo,ementa,cargaHoraria,departamento(nome)),ultimaAula(data),proximaAula(data),avaliacoes(itens(id,nome,nomeResumido,nota,avaliacoes(itens(id,ordinal,nomeResumido,data,nome,peso,nota(valor))))),periodoLetivo(codigo))))",
        @Query("embutir", encoded = true) append: String = "itens(turmas(itens(resultado,classes(itens(atividadeCurricular,professores(itens(pessoa)),alocacoes(itens(espacoFisico,horario)))),atividadeCurricular(departamento(nome)),ultimaAula,proximaAula,avaliacoes(itens(avaliacoes(itens(nota)))),periodoLetivo(codigo))))"
    ): Items<SemesterCompleteDTO>

    @GET("diario/periodos-letivos/{semesterId}")
    suspend fun gradesSpicy(
        @Header("Authorization") auth: String,
        @Path("semesterId") semesterId: Long,
        @Query("idPessoa") id: Long,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "id,codigo,descricao,turmas(itens(id,limiteFaltas,resultado(-%24link),classes(itens(aulas(proximaPagina,itens(planoAula,ordinal,data,situacao,assunto,materiaisApoio,tarefa)),atividadeCurricular(id,ementa,cargaHoraria),id,descricao,tipo,professores(itens(pessoa(id,nome,email,tipoPessoa))),alocacoes(itens(espacoFisico,horario)))),atividadeCurricular(id,nome,codigo,ementa,cargaHoraria,departamento(nome)),ultimaAula(data),proximaAula(data),avaliacoes(itens(nome,nomeResumido,nota,avaliacoes(itens(ordinal,nomeResumido,data,peso,nota(valor))))),periodoLetivo(codigo)))",
        @Query("embutir", encoded = true) append: String = "turmas(itens(resultado,classes(itens(aulas(itens(materiaisApoio)),atividadeCurricular,professores(itens(pessoa)),alocacoes(itens(espacoFisico,horario)))),atividadeCurricular(departamento(nome)),ultimaAula,proximaAula,avaliacoes(itens(avaliacoes(itens(nota)))),periodoLetivo(codigo)))"
    ): SemesterCompleteDTO

    @GET("diario/periodos-letivos")
    suspend fun allSemestersWithInfo(
        @Header("Authorization") auth: String,
        @Query("idPessoa") id: Long,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "itens(id,codigo,descricao,turmas(itens(id,limiteFaltas,resultado(-%24link),classes(itens(aulas(proximaPagina,itens(planoAula,ordinal,data,situacao,assunto,materiaisApoio,tarefa)),atividadeCurricular(id,ementa,cargaHoraria),id,descricao,tipo,professores(itens(pessoa(id,nome,email,tipoPessoa))),alocacoes(itens(espacoFisico,horario)))),atividadeCurricular(id,nome,codigo,ementa,cargaHoraria,departamento(nome)),ultimaAula(data),proximaAula(data),avaliacoes(itens(id,nome,nomeResumido,nota,avaliacoes(itens(id,ordinal,nomeResumido,data,peso,nota(valor))))),periodoLetivo(codigo))))",
        @Query("embutir", encoded = true) append: String = "itens(turmas(itens(resultado,classes(itens(aulas(itens(materiaisApoio)),atividadeCurricular,professores(itens(pessoa)),alocacoes(itens(espacoFisico,horario)))),atividadeCurricular(departamento(nome)),ultimaAula,proximaAula,avaliacoes(itens(avaliacoes(itens(nota)))),periodoLetivo(codigo))))"
    ): Items<SemesterCompleteDTO>

    @GET("diario/turmas")
    suspend fun classes(
        @Header("Authorization") auth: String,
        @Query("idPessoa") profileId: Long,
        @Query("idPeriodoLetivo") semesterId: Long,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "itens(id,atividadeCurricular(id,codigo,nome,ementa,cargaHoraria),classes(itens(id,tipo,descricao,alocacoes(itens(espacoFisico,horario)))))",
        @Query("embutir", encoded = true) append: String = "itens(atividadeCurricular,classes(itens(alocacoes(itens(espacoFisico,horario)))))",
        @Query("quantidade") amount: Int = 0
    ): Items<DisciplineCompleteDTO>

    @GET("diario/recados")
    suspend fun messages(
        @Header("Authorization") auth: String,
        @Query("idPessoa") profileId: Long,
        @Query("ate") until: String = "",
        @Query("quantidade") amount: Int = 10,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "itens(id,descricao,timeStamp,remetente(nome),perfilRemetente,escopos(itens(id,tipo,classe(id,descricao,tipo,atividadeCurricular(id,codigo,nome,nomeResumido,ementa,cargaHoraria,departamento(nome)))))),maisAntigos",
        @Query("embutir", encoded = true) append: String = "itens(remetente,escopos(itens(classe(atividadeCurricular(departamento)))))",
    ): ItemsTimed<MessageDTO>

    @GET("diario/turmas/{classId}")
    suspend fun classDetails(
        @Header("Authorization") auth: String,
        @Path("classId") classId: Long,
        @Query("idPessoa") profileId: Long,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "id,resultado(-%24link),limiteFaltas,classes(itens(id,descricao,tipo,professores(itens(pessoa(nome))),atividadeCurricular(id,ementa,cargaHoraria),alocacoes,aulas(proximaPagina,itens(planoAula,ordinal,data,situacao,assunto,materiaisApoio,tarefa)))),atividadeCurricular(codigo,nome,cargaHoraria,departamento,ementa,id),ultimaAula(data),proximaAula(data),periodoLetivo(codigo,descricao,inicioAulas,fimAulas)",
        @Query("embutir", encoded = true) append: String = "classes(itens(aulas(itens(materiaisApoio)),professores(itens(pessoa)),atividadeCurricular,alocacoes(itens(horario)))),ultimaaula,atividadeCurricular(departamento),resultado,periodoLetivo"
    ): DisciplineCompleteDTO

    @GET("diario/aulas")
    suspend fun lectures(
        @Header("Authorization") auth: String,
        @Query("idClasse") classId: Long,
        @Query("quantidade") limit: Int = 0,
        @Query("tokenPagina") offset: Int = 0,
        @Query("campos", encoded = true) fields: String = "proximaPagina,itens(planoAula,ordinal,data,situacao,assunto,materiaisApoio,tarefa)",
        @Query("embutir", encoded = true) append: String = "itens(materiaisApoio)"
    ): Items<LectureDTO>

    @GET("diario/faltas")
    suspend fun absences(
        @Header("Authorization") auth: String,
        @Query("idTurma") classId: Long,
        @Query("idPessoa") profileId: Long,
        @Query("quantidade") limit: Int = 0,
        @Query("tokenPagina") offset: Int = 0,
        @Query("perfil") profile: Int = 1,
        @Query("campos", encoded = true) fields: String = "proximaPagina,itens(id,abonada,retroativa,aula(id,ordinal,situacao,assunto,extra,data))",
        @Query("embutir", encoded = true) append: String = "itens(aula)"
    ): Items<MissedLectureDTO>

    @GET("diario/solicitacoes-servico")
    suspend fun serviceRequests(
        @Header("Authorization") auth: String,
        @Query("idAluno") profileId: Long,
        @Query("embutir") append: String = "itens(situacao)"
    ): Any

    @GET("diario/eventos-academicos")
    suspend fun events(
        @Header("Authorization") auth: String,
        @Query("idPessoa") id: Long,
        @Query("desde", encoded = true) since: String, //2020-08-01T00:00:00.000Z
        @Query("ate", encoded = true) until: String //2020-08-31T00:00:00.000Z
    ): Any

    @GET("diario/debitos")
    suspend fun debts(
        @Header("Authorization") auth: String,
        @Query("idAluno") id: Long
    ): Any

    @GET("diario/solicitacao-aluno")
    suspend fun course(
        @Header("Authorization") auth: String,
        @Query("idAluno") id: Long
    ): Items<CourseDataDTO>
}