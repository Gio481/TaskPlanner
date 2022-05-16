package com.example.taskplanner.domain.usecase.auth.signin

import com.example.taskplanner.domain.repository.auth.AuthRepository
import com.example.taskplanner.util.dataFetcher
import com.google.firebase.auth.AuthResult

class SignInUseCaseImpl(
    private val repository: AuthRepository,
) : SignInUseCase {

    override suspend fun signIn(
        email: String,
        password: String,
        errorAction: (error: String) -> Unit,
    ): AuthResult? {
        return dataFetcher({ repository.signIn(email, password) }, { errorAction(it) })
    }
}