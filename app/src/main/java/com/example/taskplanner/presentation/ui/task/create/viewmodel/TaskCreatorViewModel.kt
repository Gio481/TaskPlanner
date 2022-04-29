package com.example.taskplanner.presentation.ui.task.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCase
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import kotlinx.coroutines.launch

class TaskCreatorViewModel(private val taskCreatorUseCase: TaskCreatorUseCase) : ViewModel(),
    GetErrorMessage {

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun createProject(
        projectId: String,
        title: String,
        description: String,
        startDate: Long,
        endDate: Long,
    ) {
        viewModelScope.launch {
            val taskDomain = TaskDomain(title = title,
                description = description,
                startDate = startDate,
                endDate = endDate)
            taskCreatorUseCase.createTask(projectId = projectId, taskDomain = taskDomain)
        }
    }

    override fun errorMessage(message: String) {
        _errorLiveData.postValue(message)
    }
}