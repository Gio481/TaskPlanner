package com.example.taskplanner.presentation.ui.task.create.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class TaskCreatorViewModel(private val taskCreatorUseCase: TaskCreatorUseCase) : BaseViewModel() {

    fun createTask(
        projectId: String,
        title: String,
        description: String,
        startDate: Long,
        endDate: Long,
    ) {
        viewModelScope.launch {
            val taskDomain = TaskDomain(title = title,
                description = description,
                startDate = startDate,
                endDate = endDate)
            taskCreatorUseCase.createTask(projectId = projectId, taskDomain = taskDomain)
        }
    }
}