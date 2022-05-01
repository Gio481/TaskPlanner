package com.example.taskplanner.data.repository.auth

import android.net.Uri
import com.example.taskplanner.data.mapper.UserDtoMapper
import com.example.taskplanner.data.model.UserDto
import com.example.taskplanner.domain.mapper.UserDomainMapper
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.repository.auth.AuthRepository
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    fireStore: FirebaseFirestore,
    private val userDtoMapper: UserDtoMapper,
    private val userDomainMapper: UserDomainMapper,
    private val storage: FirebaseStorage,
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
                val uploadImage =
                    storage.getReference(userId).putFile(Uri.parse(userDomain.profileImage)).await()
                val imageUrl = uploadImage.metadata?.reference?.downloadUrl?.await().toString()
                val user = UserDomain(
                    id = userId,
                    name = userDomain.name,
                    job = userDomain.job,
                    email = userDomain.email,
                    password = userDomain.password,
                    profileImage = imageUrl
                )
                userCollection.document(userId).set(userDomainMapper.modelMapper(user)).await()
                Resources.Success(result)
            }
        }
    }

    override suspend fun updateUser(fieldName: String, updatedInfo: String): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val userId = auth.currentUser?.uid!!
                userCollection.document(userId).update(fieldName, updatedInfo).await()
                Resources.Success(Unit)
            }
        }
    }

    override suspend fun getUserInfo(): Resources<UserDomain> {
        return withContext(Dispatchers.IO){
            fetchData {
                val userId = auth.currentUser?.uid!!
                val result = userCollection.document(userId).get().await().toObject(UserDto::class.java)
                Resources.Success(userDtoMapper.modelMapper(result!!))
            }
        }
    }

    companion object {
        private const val USER_COLLECTION = "user"
    }
}