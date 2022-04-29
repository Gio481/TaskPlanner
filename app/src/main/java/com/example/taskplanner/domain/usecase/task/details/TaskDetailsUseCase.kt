package com.example.taskplanner.domain.usecase.task.details

import com.example.taskplanner.domain.model.TaskDomain

interface TaskDetailsUseCase {
    suspend fun getTaskInfo(taskId: String): TaskDomain?
    suspend fun updateTask(taskId: String, fieldName: String, updatedInfo: String)
    suspend fun deleteTask(taskId: String)
}