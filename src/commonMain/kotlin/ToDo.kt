package dev.niltsiar.kotlin_wr

import kotlinx.serialization.Serializable

@Serializable
data class ToDo(
    val id: Long,
    val text: String,
    val completed: Boolean = false
)
