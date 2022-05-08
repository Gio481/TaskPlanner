package com.example.taskplanner.domain.usecase.task.details

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Progress

interface TaskDetailsUseCase {
    suspend fun getTaskInfo(taskId: String, errorAction: (error: String) -> Unit): TaskDomain?
    suspend fun updateTask(
        taskId: String,
        taskDomain: TaskDomain,
        errorAction: (error: String) -> Unit,
    ): TaskDomain?

    suspend fun deleteTask(taskId: String, errorAction: (error: String) -> Unit)
    suspend fun updateTaskProgress(
        taskId: String,
        progress: Progress,
        errorAction: (error: String) -> Unit,
    )
}