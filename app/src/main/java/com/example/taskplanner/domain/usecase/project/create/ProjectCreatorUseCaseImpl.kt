package com.example.taskplanner.domain.usecase.project.create

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.project.create.ProjectCreatorRepository
import com.example.taskplanner.util.Resources

class ProjectCreatorUseCaseImpl(private val repository: ProjectCreatorRepository) :
    ProjectCreatorUseCase {
    override suspend fun createProject(
        projectDomain: ProjectDomain,
        action: (message: String) -> Unit,
    ) {
        return when (val data = repository.createProject(projectDomain)) {
            is Resources.Success -> Unit
            is Resources.Error -> action(data.message)
        }
    }
}