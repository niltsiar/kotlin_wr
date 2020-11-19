package dev.niltsiar.kotlin_wr

import dev.fritz2.binding.*
import dev.fritz2.dom.append
import dev.fritz2.dom.html.render
import dev.fritz2.dom.values
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.browser.window
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

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

        val remove = handle { todos, toDelete: ToDo ->
            restClient.deleteTodo(toDelete)
            todos - toDelete
        }

        init {
            action() handledBy load
        }
    }

    val inputHeader = render {
        header {
            h1 { +"¿Trello? 🙄" }

            input("new-todo") {
                placeholder = const("Oye, ¿qué tienes que hacer? 🤔")
                autofocus = const(true)

                changes.values().onEach { domNode.value = String.empty } handledBy todoStore.add
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
                todoStore.data.each()
                    .render { todo ->
                        li {
                            attr("data-id", todo.id)

                            div("view") {
                                label {
                                    +todo.text
                                }
                                button("destroy") {
                                    clicks.events.map { todo } handledBy todoStore.remove
                                }
                            }
                        }
                    }.bind()
            }
        }
    }

    val footer = render {
        footer("footer") {

        }
    }

    append("todoapp", inputHeader, mainSection, footer)
}
