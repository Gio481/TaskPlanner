package com.example.taskplanner.domain.repository.auth.signup

import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.util.Resources
import com.google.firebase.auth.AuthResult

interface SignUpRepository {
    suspend fun signUp(userDomain: UserDomain):Resources<AuthResult>
}