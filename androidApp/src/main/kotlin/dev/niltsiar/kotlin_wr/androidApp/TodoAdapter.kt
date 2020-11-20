package dev.niltsiar.kotlin_wr.androidApp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.niltsiar.kotlin_wr.ToDo
import dev.niltsiar.kotlin_wr.androidApp.databinding.ViewHolderTodoBinding

class TodoAdapter : ListAdapter<ToDo, TodoViewHolder>(TodoModelDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ViewHolderTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }
}

class TodoViewHolder(
    private val binding: ViewHolderTodoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(todo: ToDo) {
        binding.todo.text = todo.text
    }
}

private object TodoModelDiffCallback : DiffUtil.ItemCallback<ToDo>() {

    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean = oldItem == newItem
}
