package com.example.taskplanner.domain.usecase.auth.signin

import com.example.taskplanner.domain.repository.auth.signin.SignInRepository
import com.example.taskplanner.util.Resources
import com.google.firebase.auth.AuthResult

class SignInUseCaseImpl(private val repository: SignInRepository) : SignInUseCase {
    override suspend fun signIn(
        email: String,
        password: String,
        action: (message: String) -> Unit,
    ): AuthResult? {
        return when (val data = repository.signIn(email, password)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                action.invoke(data.message)
                null
            }
        }
    }
}