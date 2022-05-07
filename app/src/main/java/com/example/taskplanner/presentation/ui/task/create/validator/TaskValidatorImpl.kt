package com.example.taskplanner.presentation.ui.task.create.validator

import android.content.Context
import com.example.taskplanner.R
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.util.ValidateState

class TaskValidatorImpl(private val context: Context) : TaskValidator {

    override fun validate(taskDomain: TaskDomain, projectId: String): ValidateState {
        with(taskDomain) {
            val blankField = title.isNullOrBlank() && description.isNullOrBlank()
            val blankDate = startDate == null && endDate == null

            if (blankField) {
                return ValidateState.Error(context.getString(R.string.blank_field_error))
            }

            if (!blankField && blankDate) {
                return ValidateState.Error(context.getString(R.string.pick_date_error_text))
            }
            if (!blankField && blankDate && projectId.isBlank()) {
                return ValidateState.Error(context.getString(R.string.task_create_error_text))
            }
        }
        return ValidateState.Success
    }
}