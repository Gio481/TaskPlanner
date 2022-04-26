package com.example.taskplanner.domain.repository.task.details

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources

interface TaskDetailsRepository {
    suspend fun getTaskInfo(taskId: String): Resources<TaskDomain>
    suspend fun updateTask(
        taskId: String,
        fieldName: String,
        updatedInfo: String,
    ): Resources<Unit>

    suspend fun updateTaskProgress(taskId: String, progress: Progress): Resources<Unit>
    suspend fun deleteTask(taskId: String): Resources<Unit>
}