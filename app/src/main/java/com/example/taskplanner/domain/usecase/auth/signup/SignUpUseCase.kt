package com.example.taskplanner.domain.usecase.auth.signup

import com.example.taskplanner.domain.model.UserDomain
import com.google.firebase.auth.AuthResult

interface SignUpUseCase {
    suspend fun signUp(userDomain: UserDomain, errorAction: (error: String) -> Unit): AuthResult?
}