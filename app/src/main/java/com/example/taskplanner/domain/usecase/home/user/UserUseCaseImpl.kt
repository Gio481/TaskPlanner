package com.example.taskplanner.domain.usecase.home.user

import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.repository.auth.AuthRepository
import com.example.taskplanner.domain.repository.user.UserRepository
import com.example.taskplanner.util.dataFetcher

class UserUseCaseImpl(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : UserUseCase {

    override suspend fun getUserInfo(errorAction: (error: String) -> Unit): UserDomain? {
        return dataFetcher({ userRepository.getUserInfo() }, { errorAction(it) })
    }

    override suspend fun updateUser(userDomain: UserDomain, errorAction: (error: String) -> Unit) {
        dataFetcher({ userRepository.updateUser(userDomain) }, { errorAction(it) })
    }

    override suspend fun logOut() {
        dataFetcher({ authRepository.logOut() }, {})
    }

}