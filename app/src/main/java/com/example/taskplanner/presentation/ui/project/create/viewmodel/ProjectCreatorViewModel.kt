package com.example.taskplanner.presentation.ui.project.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCase
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import kotlinx.coroutines.launch

class ProjectCreatorViewModel(private val projectCreatorUseCase: ProjectCreatorUseCase) :
    ViewModel(), GetErrorMessage {

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun createProject(title: String, description: String, startDate: Long, endDate: Long) {
        viewModelScope.launch {
            val projectDomain = ProjectDomain(title = title,
                description = description,
                startDate = startDate,
                endDate = endDate)
            projectCreatorUseCase.createProject(projectDomain)
        }
    }

    override fun errorMessage(message: String) {
        _errorLiveData.postValue(message)
    }


}