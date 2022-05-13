package com.example.taskplanner.data.repository.project

import com.example.taskplanner.data.mapper.ProjectDtoMapper
import com.example.taskplanner.data.model.ProjectDto
import com.example.taskplanner.domain.mapper.ProjectDomainMapper
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.util.Constants.DESCRIPTION_FIELD
import com.example.taskplanner.util.Constants.END_DATE_FIELD
import com.example.taskplanner.util.Constants.START_DATE_FIELD
import com.example.taskplanner.util.Constants.TITLE_FIELD
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class ProjectRepositoryImpl(
    auth: FirebaseAuth,
    fireStore: FirebaseFirestore,
    private val projectDtoMapper: ProjectDtoMapper,
    private val projectDomainMapper: ProjectDomainMapper,
) : ProjectRepository {

    private val projectCollection = fireStore.collection(PROJECT_COLLECTION)
    private val userId = auth.currentUser?.uid

    override suspend fun getAllProjects(): Resources<List<ProjectDomain>> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    projectCollection.whereEqualTo(PROJECTS_OWNER_ID, userId).get()
                        .await()
                        .toObjects(ProjectDto::class.java)
                Resources.Success(projectDtoMapper.modelListMapper(result))
            }
        }
    }

    override suspend fun getProjectsSize(projectProgress: Progress): Resources<Int> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    projectCollection.whereEqualTo(PROJECTS_OWNER_ID, userId)
                        .whereEqualTo(PROJECT_PROGRESS_FIELD, projectProgress).get()
                        .await().documents.size
                Resources.Success(result)
            }
        }
    }

    override suspend fun createProject(projectDomain: ProjectDomain): Resources<ProjectDomain> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val projectId = UUID.randomUUID().toString()
                val project = ProjectDomain(
                    ownerId = userId,
                    projectId = projectId,
                    title = projectDomain.title,
                    description = projectDomain.description,
                    startDate = projectDomain.startDate,
                    endDate = projectDomain.endDate,
                    projectProgress = projectDomain.projectProgress
                )
                projectCollection.document(projectId).set(projectDomainMapper.modelMapper(project))
                    .await()
                getProjectInfo(projectId)
            }
        }
    }

    override suspend fun getProjectInfo(projectId: String): Resources<ProjectDomain> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    projectCollection.document(projectId).get().await()
                        .toObject(ProjectDto::class.java)
                Resources.Success(projectDtoMapper.modelMapper(result!!))
            }
        }
    }

    override suspend fun deleteProject(projectId: String): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                projectCollection.document(projectId).delete().await()
                Resources.Success(Unit)
            }
        }
    }

    override suspend fun updateProject(
        projectId: String,
        projectDomain: ProjectDomain,
    ): Resources<ProjectDomain> {
        return withContext(Dispatchers.IO) {
            fetchData {
                with(projectDomain) {
                    val dataMap: Map<String, Any> =
                        mapOf(TITLE_FIELD to title!!,
                            DESCRIPTION_FIELD to description!!,
                            START_DATE_FIELD to startDate!!,
                            END_DATE_FIELD to endDate!!)

                    projectCollection.document(projectId).update(dataMap).await()
                }
                getProjectInfo(projectId)
            }
        }
    }

    override suspend fun updateProjectProgress(
        projectId: String,
        progress: Progress,
    ): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                projectCollection.document(projectId).update(PROJECT_PROGRESS_FIELD, progress)
                    .await()
                Resources.Success(Unit)
            }
        }
    }

    companion object {
        private const val PROJECT_COLLECTION = "project"
        private const val PROJECTS_OWNER_ID = "ownerId"
        private const val PROJECT_PROGRESS_FIELD = "projectProgress"
    }
}