package com.example.taskplanner.domain.usecase.project.details.edit_project

import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Resources

class EditProjectUseCaseImpl(
    private val projectRepository: ProjectRepository,
    private val getErrorMessage: GetErrorMessage,
) : EditProjectUseCase {

    override suspend fun deleteProject(projectId: String) {
        when (val data = projectRepository.deleteProject(projectId)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }

    override suspend fun updateProject(
        projectId: String,
        fieldName: String,
        updatedInfo: String,
    ) {
        when (val data = projectRepository.updateProject(projectId, fieldName, updatedInfo)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }
}