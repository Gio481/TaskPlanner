package com.example.taskplanner.domain.usecase.splash

import com.example.taskplanner.domain.repository.auth.AuthRepository

class IsUserLoggedUseCaseImpl(private val authRepository: AuthRepository) : IsUserLoggedUseCase {
    override suspend fun isUserLogged(): Boolean {
        return authRepository.isUserLogged()
    }
}