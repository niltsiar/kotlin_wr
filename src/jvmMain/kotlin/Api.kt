package dev.niltsiar.kotlin_wr

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*

private val todos = mutableMapOf<Int, ToDo>(1 to ToDo(1, "Prueba de malandriners"))

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
}

fun Route.getTodos() = get("/todos") {
    call.respond(todos.values.toList())
}
