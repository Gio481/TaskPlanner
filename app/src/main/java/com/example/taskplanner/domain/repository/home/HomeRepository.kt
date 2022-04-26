package com.example.taskplanner.domain.repository.home

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources

interface HomeRepository {
    suspend fun getAllProjects(): Resources<List<ProjectDomain>>
    suspend fun getTodoProjectsSize(): Resources<Int>
    suspend fun getInProgressProjectSize(): Resources<Int>
    suspend fun getDoneProgressProjectSize(): Resources<Int>
    suspend fun updateUser(fieldName: String, updatedInfo: String): Resources<Unit>
    suspend fun updateProjectProgress(projectId: String, progress: Progress): Resources<Unit>
}