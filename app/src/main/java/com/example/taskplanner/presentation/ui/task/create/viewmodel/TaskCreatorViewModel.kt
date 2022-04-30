package com.example.taskplanner.presentation.ui.task.create.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.presentation.ui.project.create.validator.ProjectValidator
import com.example.taskplanner.util.ValidateState
import kotlinx.coroutines.launch

class TaskCreatorViewModel(
    private val taskCreatorUseCase: TaskCreatorUseCase,
    private val validator: ProjectValidator,
) : BaseViewModel() {

    fun createTask(
        projectId: String,
        title: String,
        description: String,
        startDate: Long,
        endDate: Long,
    ) {
        viewModelScope.launch {
            val attributeList =
                listOf(projectId, title, description, startDate.toString(), endDate.toString())
            when(val validate = validator.validate(attributeList)){
                is ValidateState.Error -> errorMessage(validate.message)
                is ValidateState.Success -> newTask(projectId, title, description, startDate, endDate)
            }
        }
    }

    private suspend fun newTask(
        projectId: String,
        title: String,
        description: String,
        startDate: Long,
        endDate: Long,
    ) {
        val taskDomain = TaskDomain(title = title,
            description = description,
            startDate = startDate,
            endDate = endDate)
        taskCreatorUseCase.createTask(projectId = projectId, taskDomain = taskDomain)
    }
}