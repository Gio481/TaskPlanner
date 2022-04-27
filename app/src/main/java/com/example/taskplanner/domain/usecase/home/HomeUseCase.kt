package com.example.taskplanner.domain.usecase.home

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.Progress

interface HomeUseCase {
    suspend fun getAllProjects(action: (message: String) -> Unit): List<ProjectDomain>?
    suspend fun getProjectsSize(progress: Progress, action: (message: String) -> Unit): Int?
    suspend fun updateUser(
        fieldName: String,
        updatedInfo: String,
        action: (message: String) -> Unit,
    )

    suspend fun updateProjectProgress(
        projectId: String,
        progress: Progress,
        action: (message: String) -> Unit,
    )
}