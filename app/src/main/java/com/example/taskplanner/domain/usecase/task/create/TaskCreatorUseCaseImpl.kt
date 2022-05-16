package com.example.taskplanner.domain.usecase.task.create

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.TaskRepository
import com.example.taskplanner.util.dataFetcher

class TaskCreatorUseCaseImpl(
    private val repository: TaskRepository,
) : TaskCreatorUseCase {

    override suspend fun createTask(
        taskDomain: TaskDomain,
        projectId: String,
        errorAction: (error: String) -> Unit,
    ): TaskDomain? {
        return dataFetcher({ repository.createTask(taskDomain, projectId) }, { errorAction(it) })
    }
}