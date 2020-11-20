package dev.niltsiar.kotlin_wr.androidApp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.niltsiar.kotlin_wr.RestClient
import dev.niltsiar.kotlin_wr.ToDo
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val jsconClient = HttpClient(OkHttp) {
        install(JsonFeature) { serializer = KotlinxSerializer() }
    }
    private val restClient = RestClient(jsconClient, BuildConfig.ENDPOINT)

    private var _todos = MutableStateFlow<List<ToDo>>(emptyList())
    val todos: StateFlow<List<ToDo>>
        get() = _todos

    fun loadTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            _todos.value = restClient.getTodos()
            Log.i("TODO", _todos.toString())
        }
    }

    fun addTodo(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTodo = restClient.addTodo(ToDo(text = text))
            _todos.value = _todos.value + newTodo
        }
    }
}
