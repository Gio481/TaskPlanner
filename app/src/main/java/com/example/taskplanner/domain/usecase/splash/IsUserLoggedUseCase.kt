package com.example.taskplanner.domain.usecase.splash

interface IsUserLoggedUseCase {
    suspend fun isUserLogged(): Boolean
}