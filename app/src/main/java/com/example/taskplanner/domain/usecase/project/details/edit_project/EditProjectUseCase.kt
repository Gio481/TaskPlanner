package com.example.taskplanner.domain.usecase.project.details.edit_project

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.Status

interface EditProjectUseCase {
    suspend fun deleteProject(projectId: String, errorAction: (error: String) -> Unit)
    suspend fun updateProject(
        projectId: String,
        projectDomain: ProjectDomain,
        errorAction: (error: String) -> Unit,
    )

    suspend fun updateProjectProgress(
        projectId: String,
        status: Status,
        errorAction: (error: String) -> Unit,
    )
}