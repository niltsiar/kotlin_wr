package dev.niltsiar.kotlin_wr

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.const
import dev.fritz2.dom.append
import dev.fritz2.dom.html.render
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.browser.window

fun main() {

    val todoStore = object : RootStore<List<ToDo>>(emptyList(), dropInitialData = true, id = "todos") {

        private val jsonClient = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
        private val restClient = RestClient(jsonClient, window.location.origin)

        val load = handle { restClient.getTodos() }

        val add = handle<String> { todos, text ->
            val newTodo = restClient.addTodo(ToDo(text = text))
            todos + newTodo
        }
    }

    val inputHeader = render {
        header {
            h1 { +"¿Trello? 🙄" }

            input("new-todo") {
                placeholder = const("Oye, ¿qué tienes que hacer? 🤔")
                autofocus = const(true)
            }
        }
    }

    val mainSection = render {
        section("main") {
            input("toggle-all", id = "toggle-all") {
                type = const("checkbox")
            }
            label(`for` = "toggle-all") {
                text("Marcar todas como completado")
            }
            ul("todo-list") {

            }
        }
    }

    val footer = render {
        footer("footer") {

        }
    }

    append("todoapp", inputHeader, mainSection, footer)
}
