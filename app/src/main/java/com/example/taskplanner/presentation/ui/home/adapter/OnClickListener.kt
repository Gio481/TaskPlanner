package com.example.taskplanner.presentation.ui.home.adapter

import com.example.taskplanner.domain.model.ProjectDomain

interface OnClickListener {
    fun onItemClickListener(projectDomain: ProjectDomain)
}