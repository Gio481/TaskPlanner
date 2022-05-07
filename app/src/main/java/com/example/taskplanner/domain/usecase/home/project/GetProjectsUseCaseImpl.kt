package com.example.taskplanner.domain.usecase.home.project

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.dataFetcher

class GetProjectsUseCaseImpl(private val projectRepository: ProjectRepository) : GetProjectsUseCase {

    override suspend fun getAllProjects(errorAction: (error: String) -> Unit): List<ProjectDomain>? {
        return dataFetcher({ projectRepository.getAllProjects() }, { errorAction(it) })
    }

    override suspend fun getProjectsSize(
        progress: Progress,
        errorAction: (error: String) -> Unit,
    ): Int? {
        return dataFetcher({ projectRepository.getProjectsSize(progress) },
            { errorAction(it) })
    }
}