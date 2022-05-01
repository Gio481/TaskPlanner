package com.example.taskplanner.domain.repository.auth

import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.util.Resources
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Resources<AuthResult>
    suspend fun signUp(userDomain: UserDomain): Resources<AuthResult>
    suspend fun updateUser(fieldName: String, updatedInfo: String): Resources<Unit>
    suspend fun getUserInfo():Resources<UserDomain>
}