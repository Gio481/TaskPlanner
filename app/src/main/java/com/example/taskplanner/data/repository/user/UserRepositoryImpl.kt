package com.example.taskplanner.data.repository.user

import com.example.taskplanner.data.mapper.UserDtoMapper
import com.example.taskplanner.data.model.UserDto
import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl.Companion.USER_COLLECTION
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.repository.user.UserRepository
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.extensions.update
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    auth: FirebaseAuth,
    fireStore: FirebaseFirestore,
    private val userDtoMapper: UserDtoMapper,
) : UserRepository {

    private val userCollection = fireStore.collection(USER_COLLECTION)
    private val userId = auth.currentUser?.uid!!
    override suspend fun updateUser(userDomain: UserDomain): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                with(userDomain) {
                    name.update {
                        userCollection.document(userId).update(USER_NAME_FIELD, name).await()
                    }
                    job.update {
                        userCollection.document(userId).update(USER_JOB_FIELD, job).await()
                    }
                    profileImage.update {
                        userCollection.document(userId)
                            .update(USER_PROFILE_IMAGE_FIELD, profileImage).await()
                    }
                }
                Resources.Success(Unit)
            }
        }
    }

    override suspend fun getUserInfo(): Resources<UserDomain> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    userCollection.document(userId).get().await().toObject(UserDto::class.java)
                Resources.Success(userDtoMapper.modelMapper(result!!))
            }
        }
    }

    companion object {
        private const val USER_NAME_FIELD = "name"
        private const val USER_JOB_FIELD = "job"
        private const val USER_PROFILE_IMAGE_FIELD = "profileImage"
    }
}