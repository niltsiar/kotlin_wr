package dev.niltsiar.kotlin_wr.androidApp

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.niltsiar.kotlin_wr.androidApp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val viewModel: TodoViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        TodoAdapter(
            deleteAction = { viewModel.deleteTodo(it) },
            completedAction = { viewModel.modifyTodo(it) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)

            todos.setHasFixedSize(true)
            todos.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            todos.addItemDecoration(VerticalMarginItemDecoration(resources.getDimensionPixelSize(R.dimen.activity_vertical_margin)))

            addNewTodo.setOnClickListener {
                viewModel.addTodo(newTodoText.text.toString())
                hideKeyboard()
                newTodoText.text?.clear()
                newTodoText.clearFocus()
            }

            adapter.submitList(emptyList())
            todos.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.todos.onEach { updatedTodos ->
            adapter.submitList(updatedTodos)
        }
            .launchIn(lifecycleScope)

        viewModel.loadTodos()
    }

    private inner class VerticalMarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.bottom = spaceHeight
        }
    }
}
