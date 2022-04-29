package com.example.taskplanner.domain.usecase.task.create

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.TaskRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Resources

class TaskCreatorUseCaseImpl(
    private val repository: TaskRepository,
    private val getErrorMessage: GetErrorMessage,
) : TaskCreatorUseCase {
    override suspend fun createTask(taskDomain: TaskDomain, projectId: String) {
        return when (val data = repository.createTask(taskDomain, projectId)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }
}