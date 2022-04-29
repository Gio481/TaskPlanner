package com.example.taskplanner.domain.usecase.project.details.edit_project

interface EditProjectUseCase {
    suspend fun deleteProject(projectId: String)
    suspend fun updateProject(projectId: String, fieldName: String, updatedInfo: String)
}