package com.example.taskplanner.domain.usecase.home.project

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.Progress

interface GetProjectsUseCase {
    suspend fun getAllProjects(errorAction: (error: String) -> Unit): List<ProjectDomain>?
    suspend fun getProjectsSize(progress: Progress, errorAction: (error: String) -> Unit): Int?
}
