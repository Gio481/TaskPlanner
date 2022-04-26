package com.example.taskplanner.presentation.ui.project.details.adapter

import com.example.taskplanner.domain.model.TaskDomain

interface OnItemClickListener {
    fun onItemClick(taskDomain: TaskDomain)
    fun onTaskProgressClick()
}