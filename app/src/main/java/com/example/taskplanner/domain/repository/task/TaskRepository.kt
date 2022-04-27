package com.example.taskplanner.domain.repository.task

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Resources

interface TaskRepository {
    suspend fun getAllTasks(projectId: String):Resources<List<TaskDomain>>
    suspend fun createTask(taskDomain: TaskDomain, projectId: String): Resources<Unit>
    suspend fun getTaskInfo(taskId: String): Resources<TaskDomain>
    suspend fun updateTask(taskId: String, fieldName: String, updatedInfo: String): Resources<Unit>
    suspend fun deleteTask(taskId: String): Resources<Unit>
}