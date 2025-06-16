package com.example.listsee.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listsee.R
import com.example.listsee.databinding.TaskItemBinding
import com.example.listsee.model.Task

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onItemClick: (String) -> Unit
): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private lateinit var context: Context
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = TaskItemBinding.bind(view)

        fun setUpUI(task: Task) {
            binding.nombre.text = task.name
            binding.description.text = task.description
            binding.fecha.text = task.date.toString()
            binding.itemContainerView.setOnClickListener {
                onItemClick(task.id)
            }
        }
    }

    fun add(taskItems: List<Task>) {
        tasks.addAll(taskItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUpUI(tasks[position])
    }
}