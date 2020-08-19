package dev.forcetower.breaker

import com.google.gson.Gson
import dev.forcetower.breaker.model.Authorization
import dev.forcetower.breaker.result.Outcome
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.system.exitProcess

fun main() {
    val gson = Gson()
    val orchestra = Orchestra.Builder().build()

    runBlocking {
        val file = File("auth.json")
        val authorization = gson.fromJson(file.readText(), Authorization::class.java)

        // val authorization = Authorization("username", "actual_password")
        orchestra.setAuthorization(authorization)

        val start = orchestra.login()
        if (start is Outcome.Error) {
            println(start.code)
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
                    clazz.allocations.sortedBy { it.time.day }.forEach { allocation ->
                        println(allocation)
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
    }
    exitProcess(0)
}