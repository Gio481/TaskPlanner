package com.example.taskplanner.domain.usecase.project.create

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Resources

class ProjectCreatorUseCaseImpl(
    private val repository: ProjectRepository,
    private val getErrorMessage: GetErrorMessage,
) : ProjectCreatorUseCase {
    override suspend fun createProject(projectDomain: ProjectDomain) {
        return when (val data = repository.createProject(projectDomain)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }
}