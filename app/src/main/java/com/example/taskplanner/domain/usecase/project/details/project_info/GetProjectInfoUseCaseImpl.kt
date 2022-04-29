package com.example.taskplanner.domain.usecase.project.details.project_info

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Resources

class GetProjectInfoUseCaseImpl(
    private val projectRepository: ProjectRepository,
    private val getErrorMessage: GetErrorMessage,
) : GetProjectInfoUseCase {

    override suspend fun getProjectInfo(projectId: String): ProjectDomain? {
        return when (val data = projectRepository.getProjectInfo(projectId)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }
}