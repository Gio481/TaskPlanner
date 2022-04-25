package com.example.taskplanner.data.repository.task.create

import com.example.taskplanner.data.mapper.task.TaskDataMapper
import com.example.taskplanner.data.model.TaskDto
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.create.TaskCreatorRepository
import com.example.taskplanner.util.Constants.TASK_COLLECTION
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class TaskCreatorRepositoryImpl(
    fireStore: FirebaseFirestore,
    auth: FirebaseAuth,
    private val taskDataMapper: TaskDataMapper<TaskDto, TaskDomain>,
) : TaskCreatorRepository {
    private val taskCollection = fireStore.collection(TASK_COLLECTION)
    private val taskId = UUID.randomUUID().toString()
    private val userId = auth.currentUser?.uid!!
    override suspend fun createTask(taskDomain: TaskDomain, projectId: String): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
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
                taskCollection.document(taskId).set(taskDataMapper.domainToDto(task)).await()
                Resources.Success(Unit)
            }
        }
    }
}