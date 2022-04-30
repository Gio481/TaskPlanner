package com.example.taskplanner.presentation.ui.auth.signup.validator

import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.util.ValidateState

interface SignUpValidator {
    fun validate(userDomain: UserDomain, repeatPassword: String): ValidateState
}