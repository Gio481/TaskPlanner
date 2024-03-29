package com.example.taskplanner.domain.usecase.project.create

import com.example.taskplanner.domain.model.ProjectDomain

interface ProjectCreatorUseCase {
    suspend fun createProject(
        projectDomain: ProjectDomain,
        errorAction: (error: String) -> Unit,
    ): ProjectDomain?
}