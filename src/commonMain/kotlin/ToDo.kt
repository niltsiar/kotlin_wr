package dev.niltsiar.kotlin_wr

import kotlinx.serialization.Serializable

@Serializable
data class ToDo(
    val id: String = String.empty,
    val text: String,
    val completed: Boolean = false
)
