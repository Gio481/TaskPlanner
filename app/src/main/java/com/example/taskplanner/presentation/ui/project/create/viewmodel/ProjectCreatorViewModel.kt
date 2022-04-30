package com.example.taskplanner.presentation.ui.project.create.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.presentation.ui.project.create.validator.ProjectValidator
import com.example.taskplanner.util.ValidateState
import kotlinx.coroutines.launch

class ProjectCreatorViewModel(
    private val projectCreatorUseCase: ProjectCreatorUseCase,
    private val validator: ProjectValidator,
) : BaseViewModel() {

    fun createProject(title: String, description: String, startDate: Long, endDate: Long) {
        viewModelScope.launch {
            val attributeList = listOf(title, description, startDate.toString(), endDate.toString())
            when (val validation = validator.validate(attributeList)) {
                is ValidateState.Error -> errorMessage(validation.message)
                is ValidateState.Success -> newProject(title, description, startDate, endDate)
            }
        }
    }

    private suspend fun newProject(
        title: String,
        description: String,
        startDate: Long,
        endDate: Long,
    ) {
        val projectDomain = ProjectDomain(title = title,
            description = description,
            startDate = startDate,
            endDate = endDate)
        projectCreatorUseCase.createProject(projectDomain)
    }

}