package com.example.taskplanner.domain.usecase.auth.signin

import com.google.firebase.auth.AuthResult

interface SignInUseCase {
    suspend fun signIn(
        email: String,
        password: String,
        action: (message: String) -> Unit,
    ): AuthResult?
}