package com.example.taskplanner.domain.usecase.task.create

import com.example.taskplanner.domain.model.TaskDomain

interface TaskCreatorUseCase {
    suspend fun createTask(
        taskDomain: TaskDomain,
        projectId: String,
        action: (message: String) -> Unit,
    )
}