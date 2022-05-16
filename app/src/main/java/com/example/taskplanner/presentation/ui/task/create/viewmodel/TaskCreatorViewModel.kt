package com.example.taskplanner.presentation.ui.task.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.presentation.ui.task.create.validator.TaskValidator
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.ValidateState
import kotlinx.coroutines.launch

class TaskCreatorViewModel(
    private val taskCreatorUseCase: TaskCreatorUseCase,
    private val validator: TaskValidator,
) : BaseViewModel() {

    var startDate: Long? = null
    var endDate: Long? = null
    var task: TaskDomain = TaskDomain()
    var project = ProjectDomain()

    private val _taskLiveData: MutableLiveData<TaskDomain> = MutableLiveData()
    val taskLiveData: LiveData<TaskDomain> = _taskLiveData

    private val _taskOwnerProjectLiveData: MutableLiveData<ProjectDomain> = MutableLiveData()
    val taskOwnerProjectLiveData: LiveData<ProjectDomain> = _taskOwnerProjectLiveData

    fun createTask(taskDomain: TaskDomain) {
        viewModelScope.launch {
            when (val validate = validator.validate(taskDomain, project.projectId!!)) {
                is ValidateState.Error -> getErrorMessage(validate.message)
                is ValidateState.Success -> _taskLiveData.postValue(taskCreatorUseCase.createTask(
                    taskDomain, project.projectId!!) { getErrorMessage(it) })
            }
        }
    }

    fun getTaskOwnerProjectInfo(projectDomain: ProjectDomain) {
        _taskOwnerProjectLiveData.postValue(projectDomain)
    }

    fun newTask(title: String, description: String): TaskDomain {
        task = TaskDomain(
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate,
            taskProgress = Progress.TODO
        )
        return task
    }
}