package com.example.taskplanner.data.repository.project.details

import com.example.taskplanner.data.mapper.project.ProjectDataMapper
import com.example.taskplanner.data.mapper.task.TaskDataMapper
import com.example.taskplanner.data.model.ProjectDto
import com.example.taskplanner.data.model.TaskDto
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.project.details.ProjectDetailsRepository
import com.example.taskplanner.util.Constants.DESCRIPTION_FIELD
import com.example.taskplanner.util.Constants.END_TIME_FIELD
import com.example.taskplanner.util.Constants.PROJECTS_OWNER_ID
import com.example.taskplanner.util.Constants.PROJECT_COLLECTION
import com.example.taskplanner.util.Constants.PROJECT_PROGRESS_FIELD
import com.example.taskplanner.util.Constants.START_TIME_FIELD
import com.example.taskplanner.util.Constants.TASK_COLLECTION
import com.example.taskplanner.util.Constants.TASK_PROGRESS_FIELD
import com.example.taskplanner.util.Constants.TITLE_FIELD
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProjectDetailsRepositoryImpl(
    auth: FirebaseAuth,
    fireStore: FirebaseFirestore,
    private val taskMapper: TaskDataMapper<TaskDto, TaskDomain>,
    private val projectMapper: ProjectDataMapper<ProjectDto, ProjectDomain>,
) : ProjectDetailsRepository {

    private val projectCollection = fireStore.collection(PROJECT_COLLECTION)
    private val taskCollection = fireStore.collection(TASK_COLLECTION)
    private val userId = auth.currentUser?.uid

    override suspend fun getAllSubTasks(projectId: String): Resources<List<TaskDomain>> {
        return withContext(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                fetchData {
                    val result =
                        taskCollection.whereEqualTo(PROJECTS_OWNER_ID, userId)
                            .whereEqualTo(PROJECT_ID, projectId).get().await()
                            .toObjects(TaskDto::class.java)
                    Resources.Success(taskMapper.dtoListToDomainList(result))
                }
            }
        }
    }

    override suspend fun getProjectInfo(projectId: String): Resources<ProjectDomain> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    projectCollection.document(projectId).get().await()
                        .toObject(ProjectDto::class.java)
                Resources.Success(projectMapper.dtoToDomain(result!!))
            }
        }
    }

    override suspend fun deleteProject(projectId: String): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                taskCollection.document(projectId).delete().await()
                Resources.Success(Unit)
            }
        }
    }

    override suspend fun updateProject(projectDomain: ProjectDomain): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val project = projectMapper.domainToDto(projectDomain)
                with(project) {
                    projectCollection.document(projectId!!).update(TITLE_FIELD, title).await()
                    projectCollection.document(projectId).update(DESCRIPTION_FIELD, description)
                        .await()
                    projectCollection.document(projectId).update(START_TIME_FIELD, startDate)
                        .await()
                    projectCollection.document(projectId).update(END_TIME_FIELD, endDate).await()
                }
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
                projectCollection.document(projectId).update(PROJECT_PROGRESS_FIELD, progress.name)
                    .await()
                Resources.Success(Unit)
            }
        }
    }

    override suspend fun updateSubTaskStatus(taskId: String, progress: Progress): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                taskCollection.document(taskId).update(TASK_PROGRESS_FIELD, progress.name).await()
                Resources.Success(Unit)
            }
        }
    }

    companion object {
        private const val PROJECT_ID = "projectId"
    }
}