package dev.niltsiar.kotlin_wr

import dev.fritz2.binding.*
import dev.fritz2.dom.append
import dev.fritz2.dom.html.HtmlElements
import dev.fritz2.dom.html.render
import dev.fritz2.dom.states
import dev.fritz2.dom.values
import dev.fritz2.lenses.buildLens
import dev.fritz2.routing.router
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.browser.window
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

data class Filter(val text: String, val function: (List<ToDo>) -> List<ToDo>)

val filters = mapOf(
    "all" to Filter("Todos") { it },
    "active" to Filter("Activos") { toDos -> toDos.filter { !it.completed } },
    "completed" to Filter("Completados") { toDos -> toDos.filter { it.completed } }
)

fun main() {

    val router = router("all")

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

        val clearCompleted = handle { todos ->
            todos.partition(ToDo::completed).let { (completed, uncompleted) ->
                completed.forEach { restClient.deleteTodo(it) }
                uncompleted
            }
        }

        val toggleAll = handle { todos, toggle: Boolean ->
            val modifiedTodos = todos.mapNotNull {
                if (it.completed != toggle) {
                    it.id to it.copy(completed = toggle)
                } else {
                    null
                }
            }.toMap()
            modifiedTodos.forEach { restClient.modifyTodo(it.value) }
            todos.map { modifiedTodos.getOrElse(it.id) { it } }
        }

        val empty = data.map { it.isEmpty() }.distinctUntilChanged()
        val count = data.map { todos -> todos.count { !it.completed } }.distinctUntilChanged()
        val allChecked = data.map { todos ->
            todos.isNotEmpty() && todos.all { it.completed }
        }.distinctUntilChanged()


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
                checked = todoStore.allChecked

                changes.states() handledBy todoStore.toggleAll
            }
            label(`for` = "toggle-all") {
                text("Marcar todas como completado")
            }
            ul("todo-list") {
                todoStore.data.combine(router) { todos, route ->
                    filters[route]?.function?.invoke(todos) ?: todos
                }
                    .each(ToDo::id)
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

    fun HtmlElements.filter(text: String, route: String) {
        li {
            a {
                className = router.map { if (it == route) "selected" else String.empty }
                href = const("#$route")
                text(text)
            }
        }
    }

    val footer = render {
        footer("footer") {
            className = todoStore.empty.map { isEmpty -> if (isEmpty) "hidden" else String.empty }

            span("todo-count") {
                strong {
                    todoStore.count.map {
                        "$it tarea${if (it != 1) "s" else String.empty} pendiente${if (it != 1) "s" else String.empty}"
                    }.bind()
                }
            }

            ul("filters") {
                filters.forEach { filter(it.value.text, it.key) }
            }

            button("clear-completed") {
                text("Borrar completadas")

                clicks handledBy todoStore.clearCompleted
            }
        }
    }

    append("todoapp", inputHeader, mainSection, footer)
}
