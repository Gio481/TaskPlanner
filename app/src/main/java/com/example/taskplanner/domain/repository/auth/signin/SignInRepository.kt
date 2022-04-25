package com.example.taskplanner.domain.repository.auth.signin

import com.example.taskplanner.util.Resources
import com.google.firebase.auth.AuthResult

interface SignInRepository {
    suspend fun signIn(email: String, password: String): Resources<AuthResult>
}