package com.example.taskplanner.presentation.ui.task.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.presentation.ui.project.create.validator.ProjectValidator
import com.example.taskplanner.presentation.ui.task.create.validator.TaskValidator
import com.example.taskplanner.util.ValidateState
import kotlinx.coroutines.launch

class TaskCreatorViewModel(
    private val taskCreatorUseCase: TaskCreatorUseCase,
    private val validator: TaskValidator,
) : BaseViewModel() {

    private val _taskLiveData: MutableLiveData<TaskDomain> = MutableLiveData()
    val taskLiveData: LiveData<TaskDomain> = _taskLiveData

    fun createTask(taskDomain: TaskDomain, projectId: String?) {
        viewModelScope.launch {
            when (val validate = validator.validate(taskDomain, projectId!!)) {
                is ValidateState.Error -> getErrorMessage(validate.message)
                is ValidateState.Success -> _taskLiveData.postValue(taskCreatorUseCase.createTask(taskDomain,
                    projectId) { getErrorMessage(it) })
            }
        }
    }
}