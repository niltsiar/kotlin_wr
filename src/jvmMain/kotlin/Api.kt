package dev.niltsiar.kotlin_wr

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.util.pipeline.*
import java.util.*

private val todos = mutableMapOf<String, ToDo>()

fun Application.api() {
    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
        anyHost()
    }

    install(Compression) {
        gzip()
    }

    routing {
        api()
    }
}

fun Route.api() = route("/api") {
    getTodos()
    createTodo()
    modifyTodo()
    deleteTodo()
}

fun Route.getTodos() = get("/todos") {
    call.respond(todos.values.toList())
}

fun Route.createTodo() = post("/todos") {
    val todo = call.receive<ToDo>()
    val randomId = generateSequence { UUID.randomUUID().toString() }
        .filter { uuid -> uuid !in todos }
        .first()
    val newTodo = todo.copy(id = randomId)
    todos += newTodo.id to newTodo
    call.respond(HttpStatusCode.Created, newTodo)
}

fun Route.modifyTodo() = put("/todos/{id}") {
    val oldTodo = getOldTodo() ?: return@put

    val newTodo = call.receive<ToDo>()
    val modifiedTodo = newTodo.copy(id = oldTodo.id)
    todos.replace(modifiedTodo.id, modifiedTodo)
    call.respond(HttpStatusCode.OK, modifiedTodo)
}

fun Route.deleteTodo() = delete("/todos/{id}") {
    val oldTodo = getOldTodo() ?: return@delete

    todos.remove(oldTodo.id)
    call.respond(HttpStatusCode.OK)
}

suspend fun PipelineContext<Unit, ApplicationCall>.getOldTodo(): ToDo? {
    val oldTodo = call.parameters["id"]?.let { todos[it] }
    if (null == oldTodo) {
        call.respond(HttpStatusCode.BadRequest, mapOf("error" to "invalid id"))
    }
    return oldTodo
}
