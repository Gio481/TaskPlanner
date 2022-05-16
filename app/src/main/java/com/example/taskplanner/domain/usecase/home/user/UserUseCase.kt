package com.example.taskplanner.domain.usecase.home.user

import com.example.taskplanner.domain.model.UserDomain

interface UserUseCase {
    suspend fun getUserInfo(errorAction: (error: String) -> Unit): UserDomain?
    suspend fun updateUser(userDomain: UserDomain, errorAction: (error: String) -> Unit)
    suspend fun logOut()
}