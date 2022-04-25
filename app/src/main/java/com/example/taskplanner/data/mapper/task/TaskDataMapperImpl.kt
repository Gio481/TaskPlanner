package com.example.taskplanner.data.mapper.task

import com.example.taskplanner.data.model.TaskDto
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.Progress

class TaskDataMapperImpl : TaskDataMapper<TaskDto, TaskDomain> {
    override fun dtoListToDomainList(dto: List<TaskDto>): List<TaskDomain> {
        return dto.map {
            TaskDomain(
                taskId = it.taskId,
                ownerId = it.ownerId,
                projectId = it.projectId,
                title = it.title,
                description = it.description,
                startDate = it.startDate,
                endDate = it.endDate,
                taskProgress = Progress.valueOf(it.taskProgress!!)
            )
        }
    }

    override fun domainToDto(domain: TaskDomain): TaskDto {
        return TaskDto(
            taskId = domain.taskId,
            ownerId = domain.ownerId,
            projectId = domain.projectId,
            title = domain.title,
            description = domain.description,
            startDate = domain.startDate,
            endDate = domain.endDate,
            taskProgress = domain.taskProgress?.name
        )
    }

    override fun dtoToDomain(dto: TaskDto): TaskDomain {
        return TaskDomain(
            taskId = dto.taskId,
            ownerId = dto.ownerId,
            projectId = dto.projectId,
            title = dto.title,
            description = dto.description,
            startDate = dto.startDate,
            endDate = dto.endDate,
            taskProgress = Progress.valueOf(dto.taskProgress!!)
        )
    }
}