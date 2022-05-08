package com.example.taskplanner.domain.repository.project

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources

interface ProjectRepository {
    suspend fun getAllProjects():Resources<List<ProjectDomain>>
    suspend fun getProjectsSize(projectProgress:Progress):Resources<Int>
    suspend fun createProject(projectDomain: ProjectDomain): Resources<ProjectDomain>
    suspend fun getProjectInfo(projectId: String): Resources<ProjectDomain>
    suspend fun deleteProject(projectId: String): Resources<Unit>
    suspend fun updateProject(projectId: String, projectDomain:ProjectDomain): Resources<ProjectDomain>
    suspend fun updateProjectProgress(projectId: String, progress: Progress):Resources<Unit>
}