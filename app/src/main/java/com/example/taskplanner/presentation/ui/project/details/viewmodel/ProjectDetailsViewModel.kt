package com.example.taskplanner.presentation.ui.project.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.project.details.ProjectDetailsUseCase
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectDetailsViewModel(private val projectDetailsUseCase: ProjectDetailsUseCase) :
    ViewModel(), GetErrorMessage {

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    private val _projectInfoLiveData: MutableLiveData<ProjectDomain> = MutableLiveData()
    val projectInfoLiveData: LiveData<ProjectDomain> = _projectInfoLiveData

    private val _getAllSubTasksLiveData: MutableLiveData<List<TaskDomain>> = MutableLiveData()
    val getAllSubTasksLiveData: LiveData<List<TaskDomain>> = _getAllSubTasksLiveData


    override fun errorMessage(message: String) {
        _errorMessage.postValue(message)
    }

    fun getAllSubTasks(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _getAllSubTasksLiveData.postValue(projectDetailsUseCase.getAllSubTasks(projectId))
        }
    }

    fun getProjectInfo(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _projectInfoLiveData.postValue(projectDetailsUseCase.getProjectInfo(projectId))
        }
    }

    fun updateProject(projectId: String, fieldName: String, updatedInfo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            projectDetailsUseCase.updateProject(projectId, fieldName, updatedInfo)
        }
    }

    fun updateSubTaskProgress(taskId: String, fieldName: String, progress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            projectDetailsUseCase.updateSubTaskStatus(taskId, fieldName, progress)
        }
    }

    fun deleteProject(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            projectDetailsUseCase.deleteProject(projectId)
        }
    }

}