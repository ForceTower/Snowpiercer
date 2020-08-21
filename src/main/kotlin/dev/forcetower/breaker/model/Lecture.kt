package dev.forcetower.breaker.model

import dev.forcetower.breaker.dto.base.LectureDTO

data class Lecture(
    val ordinal: Int,
    val situation: Int,
    val date: String,
    val subject: String,
    val materials: List<LectureMaterial>
) {
    companion object {
        fun fromDTO(lecture: LectureDTO): Lecture {
            return Lecture(
                lecture.ordinal,
                lecture.situation,
                lecture.date,
                lecture.subject,
                lecture.materials?.items?.map { material ->
                    LectureMaterial(
                        material.id,
                        material.description,
                        material.url.link.href
                    )
                } ?: emptyList()
            )
        }
    }
}