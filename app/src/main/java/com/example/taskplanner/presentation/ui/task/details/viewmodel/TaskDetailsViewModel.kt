package com.example.taskplanner.presentation.ui.task.details.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailsViewModel(private val taskDetailsUseCase: TaskDetailsUseCase) : BaseViewModel() {

    fun updateTask(taskId: String, taskDomain: TaskDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDetailsUseCase.updateTask(taskId, taskDomain) { getErrorMessage(it) }
        }
    }

    fun updateTaskProgress(taskId: String?, progress: Status) {
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