package com.example.taskplanner.domain.repository.project.create

import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.Resources

interface ProjectCreatorRepository {
    suspend fun createProject(projectDomain: ProjectDomain): Resources<Unit>
}