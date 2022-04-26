package com.example.taskplanner.domain.repository.project.details

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources

interface ProjectDetailsRepository {
    suspend fun getAllSubTasks(projectId: String): Resources<List<TaskDomain>>
    suspend fun getProjectInfo(projectId: String): Resources<ProjectDomain>
    suspend fun deleteProject(projectId: String): Resources<Unit>
    suspend fun updateProject(
        projectId: String,
        fieldName: String,
        updatedInfo: String,
    ): Resources<Unit>
    suspend fun updateProjectProgress(projectId: String, progress: Progress): Resources<Unit>
    suspend fun updateSubTaskStatus(taskId: String, progress: Progress): Resources<Unit>
}