package com.example.taskplanner.presentation.ui.auth.signup.validator

import android.content.Context
import com.example.taskplanner.R
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.util.ValidateState

class SignUpValidatorImpl(private val context: Context) : SignUpValidator {

    override fun validate(userDomain: UserDomain, repeatPassword: String): ValidateState {
        with(userDomain) {
            val containsLettersAndDigits = password!!.any { it.isDigit() } &&
                    password.any { it.isLetter() }

            if (name.isNullOrBlank() && job.isNullOrBlank() && email.isNullOrBlank() && password.isNullOrBlank() && repeatPassword.isBlank()) {
                return ValidateState.Error(context.getString(R.string.blank_field_error))
            } else if (!containsLettersAndDigits) {
                return ValidateState.Error(context.getString(R.string.password_error_text))
            }

            if (password != repeatPassword) {
                return ValidateState.Error(context.getString(R.string.passwords_math_error))
            }
        }
        return ValidateState.Success
    }
}