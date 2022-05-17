package com.example.taskplanner.domain.usecase.task.details

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.TaskRepository
import com.example.taskplanner.util.Status
import com.example.taskplanner.util.dataFetcher

class TaskDetailsUseCaseImpl(
    private val taskRepository: TaskRepository,
) : TaskDetailsUseCase {

    override suspend fun getTaskInfo(
        taskId: String,
        errorAction: (error: String) -> Unit,
    ): TaskDomain? {
        return dataFetcher({ taskRepository.getTaskInfo(taskId) }, { errorAction(it) })
    }

    override suspend fun updateTask(
        taskId: String,
        taskDomain: TaskDomain,
        errorAction: (error: String) -> Unit,
    ):TaskDomain? {
       return dataFetcher({ taskRepository.updateTask(taskId, taskDomain) },
            { errorAction(it) })
    }

    override suspend fun deleteTask(taskId: String, errorAction: (error: String) -> Unit) {
        dataFetcher({ taskRepository.deleteTask(taskId) }, { errorAction(it) })
    }

    override suspend fun updateTaskProgress(
        taskId: String,
        status: Status,
        errorAction: (error: String) -> Unit,
    ) {
        dataFetcher({ taskRepository.updateTaskProgress(taskId, status) }, { errorAction(it) })
    }
}