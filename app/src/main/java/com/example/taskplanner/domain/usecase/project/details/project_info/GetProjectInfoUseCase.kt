package com.example.taskplanner.domain.usecase.project.details.project_info

import com.example.taskplanner.domain.model.ProjectDomain

interface GetProjectInfoUseCase {
    suspend fun getProjectInfo(
        projectId: String,
        errorAction: (error: String) -> Unit,
    ): ProjectDomain?
}