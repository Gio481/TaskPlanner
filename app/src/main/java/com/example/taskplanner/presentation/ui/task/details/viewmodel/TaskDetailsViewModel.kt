package com.example.taskplanner.presentation.ui.task.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCase
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailsViewModel(private val taskDetailsUseCase: TaskDetailsUseCase) : ViewModel(),
    GetErrorMessage {

    private val _errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val errorMessageLiveData: LiveData<String> = _errorMessageLiveData

    private val _taskInfoLiveData: MutableLiveData<TaskDomain> = MutableLiveData()
    val taskInfoLiveData: LiveData<TaskDomain> = _taskInfoLiveData

    override fun errorMessage(message: String) {
        _errorMessageLiveData.postValue(message)
    }

    fun getTaskInfo(taskId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _taskInfoLiveData.postValue(taskDetailsUseCase.getTaskInfo(taskId))
        }
    }

    fun updateTask(taskId: String, fieldName: String, updatedInfo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDetailsUseCase.updateTask(taskId, fieldName, updatedInfo)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDetailsUseCase.deleteTask(taskId)
        }
    }

}