package com.example.taskplanner.domain.usecase.project.details

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Progress

interface ProjectDetailsUseCase {
    suspend fun getAllSubTasks(projectId: String): List<TaskDomain>?
    suspend fun getProjectInfo(projectId: String): ProjectDomain?
    suspend fun deleteProject(projectId: String)
    suspend fun updateProject(projectId: String, fieldName: String, updatedInfo: String)
    suspend fun updateSubTaskStatus(taskId: String, fieldName: String, progress: String)
}