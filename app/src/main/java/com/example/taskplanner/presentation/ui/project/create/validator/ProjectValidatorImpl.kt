package com.example.taskplanner.presentation.ui.project.create.validator

import android.content.Context
import com.example.taskplanner.R
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.util.ValidateState

class ProjectValidatorImpl(private val context: Context) : ProjectValidator {

    override fun validate(projectDomain: ProjectDomain): ValidateState {
        with(projectDomain) {
            val blankField = title.isNullOrBlank() && description.isNullOrBlank()
            if (blankField) {
                return ValidateState.Error(context.getString(R.string.blank_field_error))
            }

            if (!blankField && startDate == null && endDate == null) {
                return ValidateState.Error(context.getString(R.string.pick_date_error_text))
            }
        }
        return ValidateState.Success
    }
}