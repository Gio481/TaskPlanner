package com.example.taskplanner.presentation.ui.task.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.util.Progress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailsViewModel(private val taskDetailsUseCase: TaskDetailsUseCase) : BaseViewModel() {

    private val _taskLiveData: MutableLiveData<TaskDomain> = MutableLiveData()
    val taskLiveData: LiveData<TaskDomain> = _taskLiveData

    private val _projectDateLiveData: MutableLiveData<ProjectDomain> = MutableLiveData()
    val projectDateLiveData: LiveData<ProjectDomain> = _projectDateLiveData

    var task = TaskDomain()
    var startDate: Long? = null
    var endDate: Long? = null
    var isFinishedTask = false
    var projectStartDate: Long? = null
    var projectEndDate: Long? = null

    fun getTaskInfo(taskDomain: TaskDomain) = _taskLiveData.postValue(taskDomain)

    fun getProjectDate(projectDomain: ProjectDomain) = _projectDateLiveData.postValue(projectDomain)

    fun updateTask(title: String? = task.title, description: String? = task.description) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDomain = TaskDomain(
                title = title?.ifBlank { task.title },
                description = description,
                startDate = startDate,
                endDate = endDate
            )
            _taskLiveData.postValue(taskDetailsUseCase.updateTask(task.taskId!!,
                taskDomain) { getErrorMessage(it) })
        }
    }


    fun updateTaskProgress(progress: Progress) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDetailsUseCase.updateTaskProgress(task.taskId!!, progress) { getErrorMessage(it) }
        }
    }

    fun deleteTask(taskId: String? = task.taskId) {
        viewModelScope.launch(Dispatchers.IO) {
            successData(taskDetailsUseCase.deleteTask(taskId!!) { getErrorMessage(it) })
        }
    }

}