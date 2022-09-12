package com.example.taskplanner.domain.usecase.project.create

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.util.dataFetcher

class ProjectCreatorUseCaseImpl(
    private val repository: ProjectRepository,
) : ProjectCreatorUseCase {

    override suspend fun createProject(
        projectDomain: ProjectDomain,
        errorAction: (error: String) -> Unit,
    ): ProjectDomain? {
        return dataFetcher({ repository.createProject(projectDomain) }, { errorAction(it) })
    }
}