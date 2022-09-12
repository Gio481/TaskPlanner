package com.example.taskplanner.domain.usecase.project.details.edit_project

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.util.Status
import com.example.taskplanner.util.dataFetcher

class EditProjectUseCaseImpl(
    private val projectRepository: ProjectRepository,
) : EditProjectUseCase {

    override suspend fun deleteProject(projectId: String, errorAction: (error: String) -> Unit) {
        dataFetcher({ projectRepository.deleteProject(projectId) }, { errorAction(it) })
    }

    override suspend fun updateProject(
        projectId: String,
        projectDomain: ProjectDomain,
        errorAction: (error: String) -> Unit,
    ):ProjectDomain? {
        return dataFetcher({ projectRepository.updateProject(projectId, projectDomain) },
            { errorAction(it) })
    }

    override suspend fun updateProjectProgress(
        projectId: String,
        status: Status,
        errorAction: (error: String) -> Unit,
    ) {
        dataFetcher({ projectRepository.updateProjectProgress(projectId, status) },
            { errorAction(it) })
    }
}