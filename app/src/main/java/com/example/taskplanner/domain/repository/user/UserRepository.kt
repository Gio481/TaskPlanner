package com.example.taskplanner.domain.repository.user

import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.util.Resources

interface UserRepository {
    suspend fun updateUser(userDomain: UserDomain): Resources<Unit>
    suspend fun getUserInfo(): Resources<UserDomain>
}