package com.example.taskplanner.domain.usecase.project.details.project_info

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.util.dataFetcher

class GetProjectInfoUseCaseImpl(
    private val projectRepository: ProjectRepository,
) : GetProjectInfoUseCase {

    override suspend fun getProjectInfo(
        projectId: String,
        errorAction: (error: String) -> Unit,
    ): ProjectDomain? {
        return dataFetcher({ projectRepository.getProjectInfo(projectId) }, { errorAction(it) })
    }
}