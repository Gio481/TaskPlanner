package com.example.taskplanner.domain.mapper

import com.example.taskplanner.data.model.ProjectDto
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.DataMapper

class ProjectDomainMapper : DataMapper<ProjectDomain, ProjectDto> {

    override fun modelMapper(model: ProjectDomain): ProjectDto {
        return ProjectDto(
            projectId = model.projectId,
            ownerId = model.ownerId,
            title = model.title,
            description = model.title,
            startDate = model.startDate,
            endDate = model.endDate,
            projectProgress = model.projectProgress
        )
    }
}