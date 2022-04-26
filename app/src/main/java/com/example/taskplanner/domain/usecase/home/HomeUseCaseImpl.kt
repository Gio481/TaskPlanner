package com.example.taskplanner.domain.usecase.home

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.home.HomeRepository
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources

class HomeUseCaseImpl(private val repository: HomeRepository) : HomeUseCase {

    override suspend fun getAllProjects(action: (message: String) -> Unit): List<ProjectDomain>? {
        return when (val data = repository.getAllProjects()) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                action(data.message)
                null
            }
        }
    }

    override suspend fun getTodoProjectsSize(action: (message: String) -> Unit): Int? {
        return when (val data = repository.getTodoProjectsSize()) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                action(data.message)
                null
            }
        }
    }

    override suspend fun getInProgressProjectSize(action: (message: String) -> Unit): Int? {
        return when (val data = repository.getInProgressProjectSize()) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                action(data.message)
                null
            }
        }
    }

    override suspend fun getDoneProgressProjectSize(action: (message: String) -> Unit): Int? {
        return when (val data = repository.getDoneProgressProjectSize()) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                action(data.message)
                null
            }
        }
    }

    override suspend fun updateUser(
        fieldName: String,
        updatedInfo: String,
        action: (message: String) -> Unit,
    ) {
        return when (val data = repository.updateUser(fieldName, updatedInfo)) {
            is Resources.Success -> Unit
            is Resources.Error -> action(data.message)
        }
    }

    override suspend fun updateProjectProgress(
        projectId: String,
        progress: Progress,
        action: (message: String) -> Unit,
    ) {
        return when (val data = repository.updateProjectProgress(projectId, progress)) {
            is Resources.Success -> Unit
            is Resources.Error -> action(data.message)
        }
    }
}