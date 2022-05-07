package com.example.taskplanner.domain.usecase.auth.signin

import com.google.firebase.auth.AuthResult

interface SignInUseCase {
    suspend fun signIn(
        email: String,
        password: String,
        errorAction: (error: String) -> Unit,
    ): AuthResult?
}