package com.example.taskplanner.domain.usecase.task.details

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.details.TaskDetailsRepository
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources

class TaskDetailsUseCaseImpl(private val repository: TaskDetailsRepository) : TaskDetailsUseCase {

    override suspend fun getTaskInfo(
        taskId: String,
        action: (message: String) -> Unit,
    ): TaskDomain? {
        return when (val data = repository.getTaskInfo(taskId)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                action.invoke(data.message)
                null
            }
        }
    }

    override suspend fun updateTask(
        taskId: String,
        fieldName: String,
        updatedInfo: String,
        action: (message: String) -> Unit,
    ) {
        return when (val data = repository.updateTask(taskId, fieldName, updatedInfo)) {
            is Resources.Success -> Unit
            is Resources.Error -> action.invoke(data.message)
        }
    }

    override suspend fun updateTaskProgress(
        taskId: String,
        progress: Progress,
        action: (message: String) -> Unit,
    ) {
        return when (val data = repository.updateTaskProgress(taskId, progress)) {
            is Resources.Success -> Unit
            is Resources.Error -> action.invoke(data.message)
        }
    }

    override suspend fun deleteTask(taskId: String, action: (message: String) -> Unit) {
        return when (val data = repository.deleteTask(taskId)) {
            is Resources.Success -> Unit
            is Resources.Error -> action.invoke(data.message)
        }
    }


}