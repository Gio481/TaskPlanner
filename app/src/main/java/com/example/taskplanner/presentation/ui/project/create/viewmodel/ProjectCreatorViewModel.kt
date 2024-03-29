package com.example.taskplanner.presentation.ui.project.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.presentation.ui.project.create.validator.ProjectValidator
import com.example.taskplanner.util.Status
import com.example.taskplanner.util.ValidateState
import kotlinx.coroutines.launch

class ProjectCreatorViewModel(
    private val projectCreatorUseCase: ProjectCreatorUseCase,
    private val validator: ProjectValidator,
) : BaseViewModel() {

    var startDate: Long? = null
    var endDate: Long? = null
    var project: ProjectDomain = ProjectDomain()

    private val _projectDomainLiveData: MutableLiveData<ProjectDomain> = MutableLiveData()
    val projectDomainLiveData: LiveData<ProjectDomain> = _projectDomainLiveData

    fun createProject(projectDomain: ProjectDomain) {
        viewModelScope.launch {
            when (val validation = validator.validate(projectDomain)) {
                is ValidateState.Error -> getErrorMessage(validation.message)
                is ValidateState.Success -> _projectDomainLiveData.postValue(projectCreatorUseCase.createProject(
                    projectDomain) { getErrorMessage(it) })
            }
        }
    }

    fun newProject(title: String, description: String): ProjectDomain {
        project = ProjectDomain(
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate,
            projectProgress = Status.TODO
        )
        return project
    }
}