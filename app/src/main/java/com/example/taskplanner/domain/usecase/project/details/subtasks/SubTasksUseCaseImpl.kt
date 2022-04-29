package com.example.taskplanner.domain.usecase.project.details.subtasks

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.TaskRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Resources

class SubTasksUseCaseImpl(
    private val taskRepository: TaskRepository,
    private val getErrorMessage: GetErrorMessage,
) : SubTasksUseCase {

    override suspend fun getAllSubTasks(projectId: String): List<TaskDomain>? {
        return when (val data = taskRepository.getAllTasks(projectId)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }

    override suspend fun updateSubTaskStatus(
        taskId: String,
        fieldName: String,
        progress: String,
    ) {
        return when (val data = taskRepository.updateTask(taskId, fieldName, progress)) {
            is Resources.Success -> data.data
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }
}