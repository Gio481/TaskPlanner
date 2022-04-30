package com.example.taskplanner.presentation.ui.project.create.validator

import com.example.taskplanner.util.ValidateState

interface ProjectValidator {
    fun validate(attributeList: List<String?>): ValidateState
}