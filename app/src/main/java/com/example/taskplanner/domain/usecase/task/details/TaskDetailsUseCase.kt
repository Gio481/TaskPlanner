package com.example.taskplanner.domain.usecase.task.details

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Progress

interface TaskDetailsUseCase {
    suspend fun getTaskInfo(
        taskId: String,
        action: (message: String) -> Unit,
    ): TaskDomain?

    suspend fun updateTask(
        taskId: String,
        fieldName: String,
        updatedInfo: String,
        action: (message: String) -> Unit,
    )

    suspend fun updateTaskProgress(
        taskId: String,
        progress: Progress,
        action: (message: String) -> Unit,
    )

    suspend fun deleteTask(taskId: String, action: (message: String) -> Unit)
}