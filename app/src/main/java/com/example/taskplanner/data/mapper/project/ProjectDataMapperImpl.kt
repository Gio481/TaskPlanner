package com.example.taskplanner.data.mapper.project

import com.example.taskplanner.data.model.ProjectDto
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.Progress

class ProjectDataMapperImpl : ProjectDataMapper<ProjectDto, ProjectDomain> {
    override fun dtoListToDomainList(dto: List<ProjectDto>): List<ProjectDomain> {
        return dto.map {
            ProjectDomain(
                ownerId = it.ownerId,
                projectId = it.projectId,
                title = it.title,
                description = it.description,
                startDate = it.startDate,
                endDate = it.endDate,
                projectProgress = Progress.valueOf(it.projectProgress!!)
            )
        }
    }

    override fun domainToDto(domain: ProjectDomain): ProjectDto {
        return ProjectDto(
            ownerId = domain.ownerId,
            projectId = domain.projectId,
            title = domain.title,
            description = domain.description,
            startDate = domain.startDate,
            endDate = domain.endDate,
            projectProgress = domain.projectProgress?.name
        )
    }

    override fun dtoToDomain(dto: ProjectDto): ProjectDomain {
        return ProjectDomain(
            ownerId = dto.ownerId,
            projectId = dto.projectId,
            title = dto.title,
            description = dto.description,
            startDate = dto.startDate,
            endDate = dto.endDate,
            projectProgress = Progress.valueOf(dto.projectProgress!!)
        )
    }
}