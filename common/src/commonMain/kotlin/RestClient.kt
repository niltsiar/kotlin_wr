package dev.niltsiar.kotlin_wr

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class RestClient(
    private val jsonClient: HttpClient,
    private val host: String
) {

    suspend fun getTodos(): List<ToDo> {
        return jsonClient.get("$host$apiPath$todoPath")
    }

    suspend fun addTodo(todo: ToDo): ToDo {
        return jsonClient.post("$host$apiPath$todoPath") {
            contentType(ContentType.Application.Json)
            body = todo
        }
    }

    suspend fun modifyTodo(todo: ToDo): ToDo {
        return jsonClient.put("$host$apiPath$todoPath/${todo.id}") {
            contentType(ContentType.Application.Json)
            body = todo
        }
    }

    suspend fun deleteTodo(todo: ToDo) {
        return jsonClient.delete("$host$apiPath$todoPath/${todo.id}")
    }
}
