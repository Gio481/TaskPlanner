package com.example.taskplanner.presentation.ui.project.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.project.details.edit_project.EditProjectUseCase
import com.example.taskplanner.domain.usecase.project.details.subtasks.SubTasksUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.util.Progress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectDetailsViewModel(
    private val editProjectUseCase: EditProjectUseCase,
    private val subTasksUseCase: SubTasksUseCase,
) : BaseViewModel() {

    private val _getAllSubTasksLiveData: MutableLiveData<List<TaskDomain>> = MutableLiveData()
    val getAllSubTasksLiveData: LiveData<List<TaskDomain>> = _getAllSubTasksLiveData

    private val _deleteProjectLiveData: MutableLiveData<Unit> = MutableLiveData()
    val deleteProjectLiveData: LiveData<Unit> = _deleteProjectLiveData

    private val _doneTasksPercentLiveData: MutableLiveData<Int> = MutableLiveData()
    val doneTasksPercentLiveData: LiveData<Int> = _doneTasksPercentLiveData

    fun getAllSubTasks(projectId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _getAllSubTasksLiveData.postValue(subTasksUseCase.getAllSubTasks(projectId!!) {
                getErrorMessage(it)
            })
        }
    }

    fun getDoneTasksPercent(projectId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _doneTasksPercentLiveData.postValue(subTasksUseCase.getDoneProjectsPercent(projectId!!) {
                getErrorMessage(it)
            })
        }
    }

    fun updateProject(projectId: String?, projectDomain: ProjectDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            editProjectUseCase.updateProject(projectId!!, projectDomain)
            { getErrorMessage(it) }
        }
    }

    fun updateProjectProgress(projectId: String?, progress: Progress) {
        viewModelScope.launch(Dispatchers.IO) {
            editProjectUseCase.updateProjectProgress(projectId!!, progress) { getErrorMessage(it) }
        }
    }

    fun updateSubTaskProgress(taskId: String, progress: Progress) {
        viewModelScope.launch(Dispatchers.IO) {
            subTasksUseCase.updateSubTaskStatus(taskId, progress) { getErrorMessage(it) }
        }
    }

    fun deleteProject(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteProjectLiveData.postValue(editProjectUseCase.deleteProject(projectId) {
                getErrorMessage(it)
            })
        }
    }
}