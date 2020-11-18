package dev.niltsiar.kotlin_wr

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*

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
        get("/") {
            call.respondText("Hola, malandriners", ContentType.Text.Plain)
        }
    }
}
