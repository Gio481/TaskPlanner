package com.example.taskplanner.domain.usecase.auth.signin

import com.example.taskplanner.domain.repository.auth.AuthRepository
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.util.Resources
import com.google.firebase.auth.AuthResult

class SignInUseCaseImpl(
    private val repository: AuthRepository,
    private val getErrorMessage: GetErrorMessage,
) : SignInUseCase {

    override suspend fun signIn(email: String, password: String): AuthResult? {
        return when (val data = repository.signIn(email, password)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                getErrorMessage.errorMessage(data.message)
                null
            }
        }
    }
}