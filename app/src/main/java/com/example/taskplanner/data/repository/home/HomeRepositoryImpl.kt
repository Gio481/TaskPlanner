package com.example.taskplanner.data.repository.home

import com.example.taskplanner.data.mapper.project.ProjectDataMapper
import com.example.taskplanner.data.model.ProjectDto
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.home.HomeRepository
import com.example.taskplanner.util.Constants.PROJECTS_OWNER_ID
import com.example.taskplanner.util.Constants.PROJECT_COLLECTION
import com.example.taskplanner.util.Constants.PROJECT_PROGRESS_FIELD
import com.example.taskplanner.util.Constants.USER_COLLECTION
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val projectMapper: ProjectDataMapper<ProjectDto, ProjectDomain>,
) : HomeRepository {

    private val userCollection = fireStore.collection(USER_COLLECTION)
    private val projectCollection = fireStore.collection(PROJECT_COLLECTION)

    override suspend fun getAllProjects(): Resources<List<ProjectDomain>> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    projectCollection.whereEqualTo(PROJECTS_OWNER_ID, auth.currentUser?.uid).get()
                        .await()
                        .toObjects(ProjectDto::class.java)
                Resources.Success(projectMapper.dtoListToDomainList(result))
            }
        }
    }

    override suspend fun getTodoProjectsSize(): Resources<Int> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    projectCollection.whereEqualTo(PROJECTS_OWNER_ID, auth.currentUser?.uid)
                        .whereEqualTo(PROJECT_PROGRESS_FIELD, Progress.Todo).get()
                        .await().documents.size
                Resources.Success(result)
            }
        }
    }

    override suspend fun getInProgressProjectSize(): Resources<Int> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    projectCollection.whereEqualTo(PROJECTS_OWNER_ID, auth.currentUser?.uid)
                        .whereEqualTo(PROJECT_PROGRESS_FIELD, Progress.InProgress).get()
                        .await().documents.size
                Resources.Success(result)
            }
        }
    }

    override suspend fun getDoneProgressProjectSize(): Resources<Int> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    projectCollection.whereEqualTo(PROJECTS_OWNER_ID, auth.currentUser?.uid)
                        .whereEqualTo(PROJECT_PROGRESS_FIELD, Progress.Done).get()
                        .await().documents.size
                Resources.Success(result)
            }
        }
    }

    override suspend fun updateUser(
        name: String,
        job: String,
        imageUrl: String,
    ): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val userId = auth.currentUser?.uid!!
                userCollection.document(userId).update(USER_NAME_FIELD, name).await()
                userCollection.document(userId).update(USER_JOB_FIELD, job).await()
                userCollection.document(userId).update(USER_PROFILE_IMAGE_FIELD, imageUrl).await()
                Resources.Success(Unit)
            }
        }
    }

    override suspend fun updateProjectProgress(
        projectId: String,
        progress: Progress,
    ): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                userCollection.document(projectId).update(PROJECT_PROGRESS_FIELD, progress.name)
                    .await()
                Resources.Success(Unit)
            }
        }
    }

    companion object {
        private const val USER_NAME_FIELD = "name"
        private const val USER_JOB_FIELD = "job"
        private const val USER_PROFILE_IMAGE_FIELD = "profileImage"
    }
}
