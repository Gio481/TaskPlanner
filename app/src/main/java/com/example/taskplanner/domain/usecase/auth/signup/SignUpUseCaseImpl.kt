package com.example.taskplanner.domain.usecase.auth.signup

import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.repository.auth.AuthRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Resources
import com.google.firebase.auth.AuthResult

class SignUpUseCaseImpl(
    private val repository: AuthRepository,
    private val getErrorMessage: GetErrorMessage,
) : SignUpUseCase {
    override suspend fun signUp(userDomain: UserDomain): AuthResult? {
        return when (val data = repository.signUp(userDomain)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }
}