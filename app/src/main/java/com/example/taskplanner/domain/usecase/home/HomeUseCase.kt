package com.example.taskplanner.domain.usecase.home

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.Progress

interface HomeUseCase {
    suspend fun getAllProjects(): List<ProjectDomain>?
    suspend fun getProjectsSize(progress: Progress): Int?
    suspend fun updateUser(fieldName: String, updatedInfo: String)
    suspend fun updateProjectProgress(projectId: String, fieldName: String, progress: Progress)
}