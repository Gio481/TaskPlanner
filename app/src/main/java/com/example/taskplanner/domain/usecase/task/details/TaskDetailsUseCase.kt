package com.example.taskplanner.domain.usecase.task.details

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Status

interface TaskDetailsUseCase {
    suspend fun getTaskInfo(taskId: String, errorAction: (error: String) -> Unit): TaskDomain?
    suspend fun updateTask(
        taskId: String,
        taskDomain: TaskDomain,
        errorAction: (error: String) -> Unit,
    )

    suspend fun deleteTask(taskId: String, errorAction: (error: String) -> Unit)
    suspend fun updateTaskProgress(
        taskId: String,
        status: Status,
        errorAction: (error: String) -> Unit,
    )
}