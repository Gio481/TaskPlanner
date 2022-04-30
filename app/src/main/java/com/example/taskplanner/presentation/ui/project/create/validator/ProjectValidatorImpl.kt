package com.example.taskplanner.presentation.ui.project.create.validator

import com.example.taskplanner.presentation.ui.auth.signup.validator.SignUpValidatorImpl.Companion.BLANK_FIELD_ERROR
import com.example.taskplanner.util.ValidateState

class ProjectValidatorImpl : ProjectValidator {

    override fun validate(attributeList: List<String?>): ValidateState {
        val blankField = attributeList.any { it.isNullOrBlank() }
        if (blankField) {
            return ValidateState.Error(BLANK_FIELD_ERROR)
        }
        return ValidateState.Success
    }

}