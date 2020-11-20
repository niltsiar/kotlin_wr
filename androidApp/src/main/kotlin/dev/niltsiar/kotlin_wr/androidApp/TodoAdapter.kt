package dev.niltsiar.kotlin_wr.androidApp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.niltsiar.kotlin_wr.ToDo
import dev.niltsiar.kotlin_wr.androidApp.databinding.ViewHolderTodoBinding

class TodoAdapter(
    private val completedAction: ((ToDo) -> Unit)? = null,
    private val deleteAction: ((ToDo) -> Unit)? = null
) : ListAdapter<ToDo, TodoViewHolder>(TodoModelDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ViewHolderTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding, completedAction, deleteAction)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }
}

class TodoViewHolder(
    private val binding: ViewHolderTodoBinding,
    private val completedAction: ((ToDo) -> Unit)? = null,
    private val deleteAction: ((ToDo) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(todo: ToDo) {
        binding.todo.text = todo.text
        binding.todo.showStrikeThrough(todo.completed)
        val completedColor = ResourcesCompat.getColor(itemView.resources, if (todo.completed) R.color.light_green else R.color.light_gray, null)
        DrawableCompat.setTint(binding.actionCompleted.drawable, completedColor)

        completedAction?.let { action ->
            binding.actionCompleted.setOnClickListener { action(todo.copy(completed = !todo.completed)) }
        }
        deleteAction?.let { action ->
            binding.actionDelete.setOnClickListener { action(todo) }
        }
    }
}

private object TodoModelDiffCallback : DiffUtil.ItemCallback<ToDo>() {

    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean = oldItem == newItem
}

fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
        if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}
