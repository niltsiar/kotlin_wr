package dev.niltsiar.kotlin_wr

import dev.fritz2.binding.const
import dev.fritz2.dom.append
import dev.fritz2.dom.html.render

fun main() {

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
