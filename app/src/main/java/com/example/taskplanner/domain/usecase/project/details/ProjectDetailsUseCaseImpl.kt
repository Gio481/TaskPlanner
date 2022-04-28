package com.example.taskplanner.domain.usecase.project.details

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.domain.repository.task.TaskRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources

class ProjectDetailsUseCaseImpl(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository,
    private val getErrorMessage: GetErrorMessage,
) : ProjectDetailsUseCase {

    override suspend fun getAllSubTasks(projectId: String): List<TaskDomain>? {
        return when (val data = taskRepository.getAllTasks(projectId)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }

    override suspend fun getProjectInfo(projectId: String): ProjectDomain? {
        return when (val data = projectRepository.getProjectInfo(projectId)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }

    override suspend fun deleteProject(projectId: String) {
        when (val data = projectRepository.deleteProject(projectId)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)

        }
    }

    override suspend fun updateProject(
        projectId: String,
        fieldName: String,
        updatedInfo: String,
    ) {
        when (val data = projectRepository.updateProject(projectId, fieldName, updatedInfo)) {
            is Resources.Success -> Unit
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }


    override suspend fun updateSubTaskStatus(
        taskId: String,
        fieldName: String,
        progress: String,
    ) {
        return when (val data = taskRepository.updateTask(taskId, fieldName, progress)) {
            is Resources.Success -> data.data
            is Resources.Error -> getErrorMessage.errorMessage(data.message)
        }
    }
}