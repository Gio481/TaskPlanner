package com.example.taskplanner.presentation.ui.auth.signup.validator

import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.util.ValidateState

class SignUpValidatorImpl : SignUpValidator {

    override fun validate(userDomain: UserDomain, repeatPassword: String): ValidateState {
        with(userDomain) {
            if (name.isNullOrBlank() && job.isNullOrBlank() && email.isNullOrBlank() && password.isNullOrBlank() && repeatPassword.isBlank()) {
                return ValidateState.Error(BLANK_FIELD_ERROR)
            }

            val containsLettersAndDigits = password!!.any { it.isDigit() } &&
                    password.any { it.isLetter() }
            if (!containsLettersAndDigits) {
                return ValidateState.Error(DIGIT_AND_LETTERS_ERROR)
            }

            if (password != repeatPassword) {
                return ValidateState.Error(PASSWORD_MATH_ERROR)
            }
        }
        return ValidateState.Success
    }

    companion object {
        const val BLANK_FIELD_ERROR = "fill the all fields"
        private const val DIGIT_AND_LETTERS_ERROR =
            "The password needs to contain at least one letter and digit"
        private const val PASSWORD_MATH_ERROR = "passwords do not match"
    }
}