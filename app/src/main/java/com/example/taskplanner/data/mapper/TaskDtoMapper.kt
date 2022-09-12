package com.example.taskplanner.data.mapper

import com.example.taskplanner.data.model.TaskDto
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.DataMapper

class TaskDtoMapper : DataMapper<TaskDto, TaskDomain> {

    override fun modelMapper(model: TaskDto): TaskDomain {
        return TaskDomain(
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