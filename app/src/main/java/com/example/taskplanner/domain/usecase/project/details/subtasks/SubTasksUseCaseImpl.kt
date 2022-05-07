package com.example.taskplanner.domain.usecase.project.details.subtasks

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.TaskRepository
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.dataFetcher

class SubTasksUseCaseImpl(
    private val taskRepository: TaskRepository,
) : SubTasksUseCase {

    override suspend fun getAllSubTasks(
        projectId: String,
        errorAction: (error: String) -> Unit,
    ): List<TaskDomain>? {
        return dataFetcher({ taskRepository.getAllTasks(projectId) }, { errorAction(it) })
    }

    override suspend fun updateSubTaskStatus(
        taskId: String,
        progress: Progress,
        errorAction: (error: String) -> Unit,
    ) {
        dataFetcher({ taskRepository.updateTaskProgress(taskId, progress) },
            { errorAction(it) })
    }

    private suspend fun getAllDoneTasks(
        projectId: String,
        errorAction: (error: String) -> Unit,
    ): Int? {
        return dataFetcher({ taskRepository.getAllDoneTasks(projectId) },
            { errorAction(it) })
    }

    private suspend fun getAllSubTasksSize(
        projectId: String,
        errorAction: (error: String) -> Unit,
    ): Int? {
        return dataFetcher({ taskRepository.getAllTasksSize(projectId) }, { errorAction(it) })
    }

    override suspend fun getDoneProjectsPercent(
        projectId: String,
        errorAction: (error: String) -> Unit,
    ): Int {
        val allTasks = getAllSubTasksSize(projectId, errorAction) ?: 0
        val doneTasks = getAllDoneTasks(projectId, errorAction) ?: 0

        return if (allTasks == 0) {
            0
        } else (100 * doneTasks) / allTasks
    }
}