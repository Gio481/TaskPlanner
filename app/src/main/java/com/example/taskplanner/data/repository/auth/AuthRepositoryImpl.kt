package com.example.taskplanner.data.repository.auth

import com.example.taskplanner.domain.mapper.UserDomainMapper
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.repository.auth.AuthRepository
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    fireStore: FirebaseFirestore,
    private val userDomainMapper: UserDomainMapper,
) : AuthRepository {

    private val userCollection = fireStore.collection(USER_COLLECTION)

    override suspend fun signIn(email: String, password: String): Resources<AuthResult> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Resources.Success(result)
            }
        }
    }

    override suspend fun signUp(userDomain: UserDomain): Resources<AuthResult> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    auth.createUserWithEmailAndPassword(userDomain.email!!, userDomain.password!!)
                        .await()
                val userId = result.user?.uid!!
                val user = UserDomain(
                    id = userId,
                    name = userDomain.name,
                    job = userDomain.job,
                    email = userDomain.email,
                    password = userDomain.password,
                    profileImage = userDomain.profileImage
                )
                userCollection.document(userId).set(userDomainMapper.modelMapper(user)).await()
                Resources.Success(result)
            }
        }
    }

    override suspend fun isUserLogged(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun logOut(): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                auth.signOut()
                Resources.Success(Unit)
            }
        }
    }

    companion object {
        const val USER_COLLECTION = "user"
    }
}