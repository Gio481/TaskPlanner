package com.example.taskplanner.presentation.ui.project.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.databinding.TaskItemLayoutBinding
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.ItemDiffUtil

class SubTasksAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<TaskDomain, SubTasksAdapter.ViewHolder>(ItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TaskItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false), onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ViewHolder(
        private val binding: TaskItemLayoutBinding,
        private val onItemClickListener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(taskDomain: TaskDomain) {
            with(binding) {
                taskTitleTextView.text = taskDomain.title
                taskProgressTextView.text = taskDomain.taskProgress?.name
                taskProgressTextView.setOnClickListener {
                    onItemClickListener.onTaskProgressClick()
                }
                showItemView.setOnClickListener {
                    onItemClickListener.onItemClick(taskDomain)
                }

            }
        }
    }

}