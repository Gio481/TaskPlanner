package com.example.taskplanner.domain.usecase.home

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.repository.auth.AuthRepository
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources

class HomeUseCaseImpl(
    private val authRepository: AuthRepository,
    private val projectRepository: ProjectRepository,
    private val getErrorMessage: GetErrorMessage,
) : HomeUseCase {

    override suspend fun getAllProjects(): List<ProjectDomain>? {
        return when (val data = projectRepository.getAllProjects()) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }

    override suspend fun getProjectsSize(progress: Progress): Int? {
        return when (val data = projectRepository.getProjectsSize(progress.value)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }

    override suspend fun updateUser(fieldName: String, updatedInfo: String) {
        return when (val data = authRepository.updateUser(fieldName, updatedInfo)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }

    override suspend fun updateProjectProgress(
        projectId: String,
        fieldName: String,
        progress: Progress,
    ) {
        return when (val data =
            projectRepository.updateProject(projectId, fieldName, progress.value)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }

    override suspend fun getUserInfo(): UserDomain? {
        return when (val data = authRepository.getUserInfo()) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }
}