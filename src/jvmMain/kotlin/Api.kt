package dev.niltsiar.kotlin_wr

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.api() {
    routing {
        get("/") {
            call.respondText("Hola, malandriners", ContentType.Text.Plain)
        }
    }
}
