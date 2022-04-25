package com.example.taskplanner.data.repository.auth.signin

import com.example.taskplanner.domain.repository.auth.signin.SignInRepository
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignInRepositoryImpl(
    private val auth: FirebaseAuth,
) : SignInRepository {
    override suspend fun signIn(email: String, password: String): Resources<AuthResult> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Resources.Success(result)
            }
        }
    }
}