package com.example.taskplanner.domain.usecase.task.create

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.create.TaskCreatorRepository
import com.example.taskplanner.util.Resources

class TaskCreatorUseCaseImpl(private val repository: TaskCreatorRepository) : TaskCreatorUseCase {
    override suspend fun createTask(
        taskDomain: TaskDomain,
        projectId: String,
        action: (message: String) -> Unit,
    ) {
        return when (val data = repository.createTask(taskDomain, projectId)) {
            is Resources.Success -> Unit
            is Resources.Error -> action.invoke(data.message)
        }
    }
}