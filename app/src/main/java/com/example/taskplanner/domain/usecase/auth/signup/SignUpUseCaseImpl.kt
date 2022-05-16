package com.example.taskplanner.domain.usecase.auth.signup

import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.repository.auth.AuthRepository
import com.example.taskplanner.util.dataFetcher
import com.google.firebase.auth.AuthResult

class SignUpUseCaseImpl(
    private val repository: AuthRepository,
) : SignUpUseCase {

    override suspend fun signUp(
        userDomain: UserDomain,
        errorAction: (error: String) -> Unit,
    ): AuthResult? {
        return dataFetcher({ repository.signUp(userDomain) }, { errorAction(it) })
    }
}