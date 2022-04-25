package com.example.taskplanner.data.repository.task.details

import com.example.taskplanner.data.mapper.task.TaskDataMapper
import com.example.taskplanner.data.model.TaskDto
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.repository.task.details.TaskDetailsRepository
import com.example.taskplanner.util.Constants.DESCRIPTION_FIELD
import com.example.taskplanner.util.Constants.END_TIME_FIELD
import com.example.taskplanner.util.Constants.START_TIME_FIELD
import com.example.taskplanner.util.Constants.TASK_COLLECTION
import com.example.taskplanner.util.Constants.TASK_PROGRESS_FIELD
import com.example.taskplanner.util.Constants.TITLE_FIELD
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.Resources
import com.example.taskplanner.util.fetchData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TaskDetailsRepositoryImpl(
    fireStore: FirebaseFirestore,
    private val taskMapper: TaskDataMapper<TaskDto, TaskDomain>,
) : TaskDetailsRepository {

    private val taskCollection = fireStore.collection(TASK_COLLECTION)

    override suspend fun getTaskInfo(taskId: String): Resources<TaskDomain> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val result =
                    taskCollection.document(taskId).get().await().toObject(TaskDto::class.java)!!
                Resources.Success(taskMapper.dtoToDomain(result))
            }
        }
    }

    override suspend fun updateTask(projectId: String, taskDomain: TaskDomain): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                val task = taskMapper.domainToDto(taskDomain)
                with(task) {
                    taskCollection.document(taskId!!).update(TITLE_FIELD, title).await()
                    taskCollection.document(taskId).update(DESCRIPTION_FIELD, description).await()
                    taskCollection.document(taskId).update(START_TIME_FIELD, startDate).await()
                    taskCollection.document(taskId).update(END_TIME_FIELD, endDate).await()
                }
                Resources.Success(Unit)
            }
        }
    }

    override suspend fun updateTaskProgress(taskId: String, progress: Progress): Resources<Unit> {
        return withContext(Dispatchers.IO) {
            fetchData {
                taskCollection.document(taskId).update(TASK_PROGRESS_FIELD, progress.name).await()
                Resources.Success(Unit)
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
}