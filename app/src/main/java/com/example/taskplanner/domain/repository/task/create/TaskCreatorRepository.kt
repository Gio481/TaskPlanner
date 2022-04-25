package com.example.taskplanner.domain.repository.task.create

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Resources

interface TaskCreatorRepository {
    suspend fun createTask(taskDomain: TaskDomain, projectId: String): Resources<Unit>

}