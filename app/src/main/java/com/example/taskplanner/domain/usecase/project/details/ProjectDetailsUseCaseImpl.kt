package com.example.taskplanner.domain.usecase.project.details

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.project.details.ProjectDetailsRepository
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources

class ProjectDetailsUseCaseImpl(private val repository: ProjectDetailsRepository) :
    ProjectDetailsUseCase {

    override suspend fun getAllSubTasks(
        projectId: String,
        action: (message: String) -> Unit,
    ): List<TaskDomain>? {
        return when (val data = repository.getAllSubTasks(projectId)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                action(data.message)
                null
            }
        }
    }

    override suspend fun getProjectInfo(
        projectId: String,
        action: (message: String) -> Unit,
    ): ProjectDomain? {
        return when (val data = repository.getProjectInfo(projectId)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                action(data.message)
                null
            }
        }
    }

    override suspend fun deleteProject(projectId: String, action: (message: String) -> Unit) {
        when (val data = repository.deleteProject(projectId)) {
            is Resources.Success -> Unit
            is Resources.Error -> action(data.message)

        }
    }

    override suspend fun updateProject(
        projectId: String,
        fieldName: String,
        updatedInfo: String,
        action: (message: String) -> Unit,
    ) {
        when (val data = repository.updateProject(projectId, fieldName, updatedInfo)) {
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

    override suspend fun updateSubTaskStatus(
        taskId: String,
        progress: Progress,
        action: (message: String) -> Unit,
    ) {
        return when (val data = repository.updateSubTaskStatus(taskId, progress)) {
            is Resources.Success -> data.data
            is Resources.Error -> action(data.message)
        }
    }
}