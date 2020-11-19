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

    append("todoapp", inputHeader)
}
