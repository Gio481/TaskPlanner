package com.example.taskplanner.domain.usecase.project.details

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Progress

interface ProjectDetailsUseCase {
    suspend fun getAllSubTasks(
        projectId: String,
        action: (message: String) -> Unit,
    ): List<TaskDomain>?

    suspend fun getProjectInfo(projectId: String, action: (message: String) -> Unit): ProjectDomain?
    suspend fun deleteProject(projectId: String, action: (message: String) -> Unit)
    suspend fun updateProject(
        projectId: String,
        fieldName: String,
        updatedInfo: String,
        action: (message: String) -> Unit,
    )

    suspend fun updateProjectProgress(
        projectId: String,
        progress: Progress,
        action: (message: String) -> Unit,
    )

    suspend fun updateSubTaskStatus(
        taskId: String,
        progress: Progress,
        action: (message: String) -> Unit,
    )
}