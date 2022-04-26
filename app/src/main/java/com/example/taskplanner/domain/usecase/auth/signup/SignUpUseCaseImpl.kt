package com.example.taskplanner.domain.usecase.auth.signup

import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.repository.auth.signup.SignUpRepository
import com.example.taskplanner.util.Resources
import com.google.firebase.auth.AuthResult

class SignUpUseCaseImpl(private val repository: SignUpRepository) : SignUpUseCase {
    override suspend fun signUp(
        userDomain: UserDomain,
        action: (message: String) -> Unit,
    ): AuthResult? {
        return when (val data = repository.signUp(userDomain)) {
            is Resources.Success -> data.data
            is Resources.Error -> {
                action.invoke(data.message)
                null
            }
        }
    }
}