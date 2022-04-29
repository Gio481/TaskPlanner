package com.example.taskplanner.presentation.ui.project.create.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class ProjectCreatorViewModel(private val projectCreatorUseCase: ProjectCreatorUseCase) :
    BaseViewModel() {

    fun createProject(title: String, description: String, startDate: Long, endDate: Long) {
        viewModelScope.launch {
            val projectDomain = ProjectDomain(title = title,
                description = description,
                startDate = startDate,
                endDate = endDate)
            projectCreatorUseCase.createProject(projectDomain)
        }
    }

}