package com.example.taskplanner.presentation.ui.project.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.databinding.TaskItemLayoutBinding
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.ItemDiffUtil
import com.example.taskplanner.util.OnItemClickListener
import com.example.taskplanner.util.ProgressListener

class SubTasksAdapter : ListAdapter<TaskDomain, SubTasksAdapter.ViewHolder>(ItemDiffUtil()) {

    lateinit var progressListener: ProgressListener
    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TaskItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(view)
        with(view) {
            taskProgressTextView.setOnClickListener {
                progressListener(it, getItem(viewHolder.adapterPosition).taskId!!)
            }
            showItemView.setOnClickListener {
                onItemClickListener(getItem(viewHolder.adapterPosition))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ViewHolder(private val binding: TaskItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(taskDomain: TaskDomain) {
            with(binding) {
                taskTitleTextView.text = taskDomain.title
                with(taskProgressTextView) {
                    text = taskDomain.taskProgress?.value?.let {
                        context.getString(it) }
                    setBackgroundColor(ContextCompat.getColor(taskProgressTextView.context,
                        taskDomain.taskProgress?.color!!))
                }
            }
        }
    }

}