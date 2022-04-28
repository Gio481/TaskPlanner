package com.example.taskplanner.domain.usecase.task.details

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.TaskRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Resources

class TaskDetailsUseCaseImpl(
    private val taskRepository: TaskRepository,
    private val getErrorMessage: GetErrorMessage,
) : TaskDetailsUseCase {

    override suspend fun getTaskInfo(taskId: String): TaskDomain? {
        return when (val data = taskRepository.getTaskInfo(taskId)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }

    override suspend fun updateTask(
        taskId: String,
        fieldName: String,
        updatedInfo: String,
    ) {
        return when (val data = taskRepository.updateTask(taskId, fieldName, updatedInfo)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }

    override suspend fun deleteTask(taskId: String) {
        return when (val data = taskRepository.deleteTask(taskId)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }


}