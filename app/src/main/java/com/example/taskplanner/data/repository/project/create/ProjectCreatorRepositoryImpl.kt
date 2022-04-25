package com.example.taskplanner.data.repository.project.create

import com.example.taskplanner.data.mapper.project.ProjectDataMapper
import com.example.taskplanner.data.model.ProjectDto
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.repository.project.create.ProjectCreatorRepository
import com.example.taskplanner.util.Constants.PROJECT_COLLECTION
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class ProjectCreatorRepositoryImpl(
    auth: FirebaseAuth,
    fireStore: FirebaseFirestore,
    private val projectMapper: ProjectDataMapper<ProjectDto, ProjectDomain>,
) : ProjectCreatorRepository {
    private val projectCollection = fireStore.collection(PROJECT_COLLECTION)
    private val userId = auth.currentUser?.uid!!
    override suspend fun createProject(projectDomain: ProjectDomain): Resources<Unit> {
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
                projectCollection.document(projectId).set(projectMapper.domainToDto(project))
                    .await()
                Resources.Success(Unit)
            }
        }
    }
}