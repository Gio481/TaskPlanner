package com.example.taskplanner.domain.usecase.project.details.subtasks

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Status

interface SubTasksUseCase {
    suspend fun getAllSubTasks(
        projectId: String,
        errorAction: (error: String) -> Unit,
    ): List<TaskDomain>?

    suspend fun updateSubTaskStatus(
        taskId: String,
        status: Status,
        errorAction: (error: String) -> Unit,
    )

    suspend fun getDoneProjectsPercent(projectId: String, errorAction: (error: String) -> Unit): Int
}