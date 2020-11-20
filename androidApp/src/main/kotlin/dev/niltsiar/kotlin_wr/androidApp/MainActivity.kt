package dev.niltsiar.kotlin_wr.androidApp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dev.niltsiar.kotlin_wr.androidApp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val viewModel: TodoViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNewTodo.setOnClickListener {
            viewModel.addTodo(binding.newTodoText.text.toString())
            hideKeyboard()
            binding.newTodoText.text?.clear()
            binding.newTodoText.clearFocus()
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.todos.onEach {

        }
            .launchIn(lifecycleScope)

        viewModel.loadTodos()
    }
}
