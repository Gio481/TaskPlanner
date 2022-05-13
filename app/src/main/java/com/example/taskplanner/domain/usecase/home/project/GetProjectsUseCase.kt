package com.example.taskplanner.domain.usecase.home.project

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.Status

interface GetProjectsUseCase {
    suspend fun getAllProjects(errorAction: (error: String) -> Unit): List<ProjectDomain>?
    suspend fun getProjectsSize(status: Status, errorAction: (error: String) -> Unit): Int?
}
