package com.example.taskplanner.data.repository.auth.signup

import android.net.Uri
import com.example.taskplanner.data.mapper.user.UserDataMapper
import com.example.taskplanner.data.model.UserDto
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.repository.auth.signup.SignUpRepository
import com.example.taskplanner.util.Constants.USER_COLLECTION
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUpRepositoryImpl(
    private val auth: FirebaseAuth,
    fireStore: FirebaseFirestore,
    private val userMapper: UserDataMapper<UserDto, UserDomain>,
    private val storage: FirebaseStorage,
) : SignUpRepository {

    private val userCollection = fireStore.collection(USER_COLLECTION)

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
                userCollection.document(userId).set(userMapper.domainToDto(user)).await()
                Resources.Success(result)
            }
        }
    }
}