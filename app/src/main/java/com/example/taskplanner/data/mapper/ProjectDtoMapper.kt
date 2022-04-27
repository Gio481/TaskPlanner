package com.example.taskplanner.data.mapper

import com.example.taskplanner.data.model.ProjectDto
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.DataMapper

class ProjectDtoMapper : DataMapper<ProjectDto, ProjectDomain> {
    override fun modelMapper(model: ProjectDto): ProjectDomain {
        return ProjectDomain(
            projectId = model.projectId,
            ownerId = model.ownerId,
            title = model.title,
            description = model.description,
            projectProgress = model.projectProgress,
            startDate = model.startDate,
            endDate = model.endDate,
        )
    }
}