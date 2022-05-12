package com.example.taskplanner.domain.usecase.home

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.util.Status

interface HomeUseCase {
    suspend fun getAllProjects(): List<ProjectDomain>?
    suspend fun getProjectsSize(progress: Status): Int?
    suspend fun updateUser(fieldName: String, updatedInfo: String)
    suspend fun updateProjectProgress(projectId: String, fieldName: String, progress: Status)
    suspend fun getUserInfo():UserDomain?
}