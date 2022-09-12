package com.example.taskplanner.presentation.ui.task.create.validator

import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.ValidateState

interface TaskValidator {
    fun validate(taskDomain: TaskDomain, projectId:String): ValidateState
}