package com.example.taskplanner.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.databinding.ProjectItemLayoutBinding
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.ItemDiffUtil

class ProjectAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ProjectDomain, ProjectAdapter.ViewHolder>(ItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProjectItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false),
            onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ViewHolder(
        private val binding: ProjectItemLayoutBinding,
        private val onClickListener: OnClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(projectDomain: ProjectDomain) {
            with(binding) {
                projectTitleTextView.text = projectDomain.title
                projectProgressTextView.text = projectDomain.projectProgress?.name
                projectItemLayout.setOnClickListener {
                    onClickListener.onItemClickListener(projectDomain)
                }
            }
        }
    }
}