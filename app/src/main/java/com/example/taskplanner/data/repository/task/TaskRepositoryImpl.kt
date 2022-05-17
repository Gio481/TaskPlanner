package com.example.taskplanner.data.repository.task

import com.example.taskplanner.data.mapper.TaskDtoMapper
import com.example.taskplanner.data.model.TaskDto
import com.example.taskplanner.domain.mapper.TaskDomainMapper
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.TaskRepository
import com.example.taskplanner.util.Constants.DESCRIPTION_FIELD
import com.example.taskplanner.util.Constants.END_DATE_FIELD
import com.example.taskplanner.util.Constants.START_DATE_FIELD
import com.example.taskplanner.util.Constants.TITLE_FIELD
import com.example.taskplanner.util.Status
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class TaskRepositoryImpl(
    fireStore: FirebaseFirestore,
    auth: FirebaseAuth,
    private val taskDtoMapper: TaskDtoMapper,
    private val taskDomainMapper: TaskDomainMapper,
) : TaskRepository {

    private val taskCollection = fireStore.collection(TASK_COLLECTION)
    private val userId = auth.currentUser?.uid

    override suspend fun getAllTasks(projectId: String): Resources<List<TaskDomain>> {
        return withContext(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                fetchData {
                    val result =
                        taskCollection.whereEqualTo(PROJECTS_OWNER_ID, userId)
                            .whereEqualTo(PROJECT_ID, projectId).get()
                            .await()
                            .toObjects(TaskDto::class.java)
                    Resources.Success(taskDtoMapper.modelListMapper(result))
                }
            }
        }
    }

    override suspend fun createTask(
        taskDomain: TaskDomain,
        projectId: String,
    ): Resources<TaskDomain> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val taskId = UUID.randomUUID().toString()
                val task = TaskDomain(
                    taskId = taskId,
                    ownerId = userId,
                    title = taskDomain.title,
                    description = taskDomain.description,
                    startDate = taskDomain.startDate,
                    endDate = taskDomain.endDate,
                    taskProgress = taskDomain.taskProgress,
                    projectId = projectId
                )
                taskCollection.document(taskId).set(taskDomainMapper.modelMapper(task)).await()
                getTaskInfo(taskId)
            }
        }
    }

    override suspend fun getTaskInfo(taskId: String): Resources<TaskDomain> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    taskCollection.document(taskId).get().await().toObject(TaskDto::class.java)!!
                Resources.Success(taskDtoMapper.modelMapper(result))
            }
        }
    }

    override suspend fun updateTask(
        taskId: String,
        taskDomain: TaskDomain,
    ): Resources<TaskDomain> {
        return withContext(Dispatchers.IO) {
            fetchData {
                with(taskDomain) {
                    val dataMap: Map<String, Any?> =
                        mapOf(TITLE_FIELD to title,
                            DESCRIPTION_FIELD to description,
                            START_DATE_FIELD to startDate,
                            END_DATE_FIELD to endDate)
                    taskCollection.document(taskId).update(dataMap).await()
                }
                getTaskInfo(taskId)
            }
        }
    }

    override suspend fun deleteTask(taskId: String): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                taskCollection.document(taskId).delete().await()
                Resources.Success(Unit)
            }
        }
    }

    override suspend fun getAllDoneTasks(projectId: String): Resources<Int> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    taskCollection.whereEqualTo(PROJECTS_OWNER_ID, userId)
                        .whereEqualTo(PROJECT_ID, projectId)
                        .whereEqualTo(TASK_PROGRESS_FIELD, Status.DONE).get()
                        .await().documents.size
                Resources.Success(result)
            }
        }
    }

    override suspend fun getAllTasksSize(projectId: String): Resources<Int> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result = taskCollection.whereEqualTo(PROJECTS_OWNER_ID, userId).whereEqualTo(
                    PROJECT_ID, projectId).get().await().documents.size
                Resources.Success(result)
            }
        }
    }

    override suspend fun updateTaskProgress(taskId: String, progress: Status): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                taskCollection.document(taskId).update(TASK_PROGRESS_FIELD, progress).await()
                Resources.Success(Unit)
            }
        }
    }

    companion object {
        private const val TASK_PROGRESS_FIELD = "taskProgress"
        private const val PROJECT_ID = "projectId"
        private const val TASK_COLLECTION = "task"
        private const val PROJECTS_OWNER_ID = "ownerId"
    }
}