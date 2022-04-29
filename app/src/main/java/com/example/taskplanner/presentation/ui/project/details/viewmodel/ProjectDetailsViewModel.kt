package com.example.taskplanner.presentation.ui.project.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.project.details.edit_project.EditProjectUseCase
import com.example.taskplanner.domain.usecase.project.details.project_info.GetProjectInfoUseCase
import com.example.taskplanner.domain.usecase.project.details.subtasks.SubTasksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectDetailsViewModel(
    private val editProjectUseCase: EditProjectUseCase,
    private val getProjectInfoUseCase: GetProjectInfoUseCase,
    private val subTasksUseCase: SubTasksUseCase,
) : ViewModel() {

    private val _projectInfoLiveData: MutableLiveData<ProjectDomain> = MutableLiveData()
    val projectInfoLiveData: LiveData<ProjectDomain> = _projectInfoLiveData

    private val _getAllSubTasksLiveData: MutableLiveData<List<TaskDomain>> = MutableLiveData()
    val getAllSubTasksLiveData: LiveData<List<TaskDomain>> = _getAllSubTasksLiveData

    fun getAllSubTasks(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _getAllSubTasksLiveData.postValue(subTasksUseCase.getAllSubTasks(projectId))
        }
    }

    fun getProjectInfo(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _projectInfoLiveData.postValue(getProjectInfoUseCase.getProjectInfo(projectId))
        }
    }

    fun updateProject(projectId: String, fieldName: String, updatedInfo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            editProjectUseCase.updateProject(projectId, fieldName, updatedInfo)
        }
    }

    fun updateSubTaskProgress(taskId: String, fieldName: String, progress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            subTasksUseCase.updateSubTaskStatus(taskId, fieldName, progress)
        }
    }

    fun deleteProject(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            editProjectUseCase.deleteProject(projectId)
        }
    }

}