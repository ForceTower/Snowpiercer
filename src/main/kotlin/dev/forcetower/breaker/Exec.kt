package dev.forcetower.breaker

import com.google.gson.Gson
import dev.forcetower.breaker.model.Authorization
import dev.forcetower.breaker.result.Outcome
import java.io.File
import kotlin.system.exitProcess

suspend fun main() {
    val gson = Gson()
    val orchestra = Orchestra.Builder().build()

    val file = File("auth.json")
    val authorization = gson.fromJson(file.readText(), Authorization::class.java)

    // val authorization = Authorization("username", "actual_password")
    orchestra.setAuthorization(authorization)

    val start = orchestra.login()
    if (start is Outcome.Error) {
        println(start.code)
        start.error.printStackTrace()
    }
    (start as? Outcome.Success)?.let { outcome ->
        val person = outcome.value
        println("Connected as ${person.name}")

        val semesters = (orchestra.semesters(person.id) as Outcome.Success).value
        println("Semesters: ${semesters.size}")
        val last = semesters.sortedByDescending { it.id }[0]

        println("Information about ${last.code}\n")

        val disciplines = (orchestra.grades(person.id, last.id) as Outcome.Success).value
        println("You have ${disciplines.size} disciplines on this semester. The details are shown below")

        disciplines.forEach { discipline ->
            println(discipline.name)
            println(discipline.program)

            discipline.classes.forEach { clazz ->
                println("Type: ${clazz.type}")
                println("Allocations are shown below")
                clazz.allocations.sortedBy { it.time?.day ?: 0 }.forEach { allocation ->
                    println(allocation)
                }

                (orchestra.lectures(clazz.id, 3, 0) as? Outcome.Success)?.let {
                    println("First 3 Lectures")
                    it.value.forEach { lecture ->
                        println(lecture.subject)
                    }
                }

                (orchestra.absences(person.id, clazz.id, 0, 0) as? Outcome.Success)?.let {
                    println("Missed classes")
                    it.value.forEach { missed ->
                        println(missed.lecture.subject)
                    }
                }
            }

            println("Evaluations...")
            discipline.evaluations.forEach { evaluation ->
                println("Evaluation: ${evaluation.name}")
                evaluation.grades.forEach { grade ->
                    println("[${grade.date}] ${grade.name} -> ${grade.value} ** ${grade.weight}")
                }
            }

            println("---------------------------------------------------")
        }

        val messages = (orchestra.messages(person.id) as Outcome.Success).value
        messages.messages.forEach { message ->
            println("Message:")
            println(message.content)
            println("From ${message.sender}")
            println("Sent at: ${message.timestamp}")
        }
    }

    exitProcess(0)
}