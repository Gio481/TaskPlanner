package com.example.taskplanner.domain.usecase.project.details.subtasks

import com.example.taskplanner.domain.model.TaskDomain

interface SubTasksUseCase {
    suspend fun getAllSubTasks(projectId: String): List<TaskDomain>?
    suspend fun updateSubTaskStatus(taskId: String, fieldName: String, progress: String)
}