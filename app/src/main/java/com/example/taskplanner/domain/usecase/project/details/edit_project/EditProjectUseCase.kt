package com.example.taskplanner.domain.usecase.project.details.edit_project

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.Progress

interface EditProjectUseCase {
    suspend fun deleteProject(projectId: String, errorAction: (error: String) -> Unit)
    suspend fun updateProject(
        projectId: String,
        projectDomain: ProjectDomain,
        errorAction: (error: String) -> Unit,
    ):ProjectDomain?

    suspend fun updateProjectProgress(
        projectId: String,
        progress: Progress,
        errorAction: (error: String) -> Unit,
    )
}