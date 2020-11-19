package dev.niltsiar.kotlin_wr

import dev.fritz2.binding.*
import dev.fritz2.dom.append
import dev.fritz2.dom.html.render
import dev.fritz2.dom.states
import dev.fritz2.dom.values
import dev.fritz2.lenses.buildLens
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

        val modify = handle<ToDo> { todos, modifiedTodo ->
            restClient.modifyTodo(modifiedTodo)
            val index = todos.indexOfFirst { it.id == modifiedTodo.id }
            val mutableTodos = todos.toMutableList()
            mutableTodos.removeAt(index)
            mutableTodos.add(index, modifiedTodo)
            mutableTodos
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
                        val elementStore = todoStore.detach(todo, ToDo::id)
                        elementStore.syncBy(todoStore.modify)
                        val completedStore = elementStore.sub(
                            buildLens(
                                "completed",
                                { it.completed },
                                { element, completed -> element.copy(completed = completed) }
                            )
                        )

                        li {
                            attr("data-id", todo.id)
                            className = elementStore.data.map { if (it.completed) "completed" else String.empty }

                            div("view") {
                                input("toggle") {
                                    type = const("checkbox")
                                    checked = completedStore.data

                                    changes.states() handledBy completedStore.update
                                }
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
