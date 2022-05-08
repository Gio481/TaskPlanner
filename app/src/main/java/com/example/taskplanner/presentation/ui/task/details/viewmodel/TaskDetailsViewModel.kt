package com.example.taskplanner.presentation.ui.task.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.util.Progress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailsViewModel(private val taskDetailsUseCase: TaskDetailsUseCase) : BaseViewModel() {

    private val _updatedTaskLiveData: MutableLiveData<TaskDomain> = MutableLiveData()
    val updatedTaskLiveData: LiveData<TaskDomain> = _updatedTaskLiveData

    fun updateTask(taskId: String, taskDomain: TaskDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            _updatedTaskLiveData.postValue(taskDetailsUseCase.updateTask(taskId,
                taskDomain) { getErrorMessage(it) })
        }
    }

    fun updateTaskProgress(taskId: String?, progress: Progress) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDetailsUseCase.updateTaskProgress(taskId!!, progress) { getErrorMessage(it) }
        }
    }

    fun deleteTask(taskId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            successData(taskDetailsUseCase.deleteTask(taskId!!) { getErrorMessage(it) })
        }
    }

}