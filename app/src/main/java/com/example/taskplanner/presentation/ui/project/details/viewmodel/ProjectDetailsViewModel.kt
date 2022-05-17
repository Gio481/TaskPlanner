package com.example.taskplanner.presentation.ui.project.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.project.details.edit_project.EditProjectUseCase
import com.example.taskplanner.domain.usecase.project.details.subtasks.SubTasksUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.util.Status
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

    private val _projectLiveData: MutableLiveData<ProjectDomain> = MutableLiveData()
    val projectLiveData: LiveData<ProjectDomain> = _projectLiveData

    var project = ProjectDomain()
    var startDate: Long? = null
    var endDate: Long? = null
    var isFinishedProject = false

    fun getProjectInfo(project: ProjectDomain) = _projectLiveData.postValue(project)

    fun getAllSubTasks(projectId: String? = project.projectId) {
        viewModelScope.launch(Dispatchers.IO) {
            _getAllSubTasksLiveData.postValue(subTasksUseCase.getAllSubTasks(projectId!!) {
                getErrorMessage(it)
            })
        }
    }

    fun getDoneTasksPercent(projectId: String? = project.projectId) {
        viewModelScope.launch(Dispatchers.IO) {
            _doneTasksPercentLiveData.postValue(subTasksUseCase.getDoneProjectsPercent(projectId!!) {
                getErrorMessage(it)
            })
        }
    }

    fun updateProject(title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newProject = ProjectDomain(
                title = title,
                description = description,
                startDate = startDate,
                endDate = endDate
            )
            _projectLiveData.postValue(editProjectUseCase.updateProject(project.projectId!!,
                newProject) { getErrorMessage(it) })
        }
    }

    fun updateProjectProgress(projectId: String? = project.projectId, progress: Status) {
        viewModelScope.launch(Dispatchers.IO) {
            editProjectUseCase.updateProjectProgress(projectId!!, progress) { getErrorMessage(it) }
        }
    }

    fun updateSubTaskProgress(taskId: String, progress: Status) {
        viewModelScope.launch(Dispatchers.IO) {
            subTasksUseCase.updateSubTaskStatus(taskId, progress) { getErrorMessage(it) }
        }
    }

    fun deleteProject(projectId: String? = project.projectId) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteProjectLiveData.postValue(editProjectUseCase.deleteProject(projectId!!) {
                getErrorMessage(it)
            })
        }
    }
}