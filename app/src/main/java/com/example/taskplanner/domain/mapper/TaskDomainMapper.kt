package com.example.taskplanner.domain.mapper

import com.example.taskplanner.data.model.TaskDto
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.DataMapper

class TaskDomainMapper : DataMapper<TaskDomain, TaskDto> {

    override fun modelMapper(model: TaskDomain): TaskDto {
        return TaskDto(
            taskId = model.taskId,
            ownerId = model.ownerId,
            projectId = model.projectId,
            title = model.title,
            description = model.description,
            startDate = model.startDate,
            endDate = model.endDate,
            taskProgress = model.taskProgress
        )
    }
}