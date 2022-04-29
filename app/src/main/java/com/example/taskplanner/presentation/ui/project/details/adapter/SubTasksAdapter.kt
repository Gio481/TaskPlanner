package com.example.taskplanner.presentation.ui.project.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.databinding.TaskItemLayoutBinding
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.ItemDiffUtil
import com.example.taskplanner.util.ProgressListener

class SubTasksAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<TaskDomain, SubTasksAdapter.ViewHolder>(ItemDiffUtil()) {

    lateinit var progressListener: ProgressListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TaskItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(view)
        with(view) {
            taskProgressTextView.setOnClickListener {
                progressListener()
            }
            showItemView.setOnClickListener {
                onItemClickListener.onItemClick(getItem(viewHolder.adapterPosition))
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
                taskProgressTextView.text = taskDomain.taskProgress

            }
        }
    }

}