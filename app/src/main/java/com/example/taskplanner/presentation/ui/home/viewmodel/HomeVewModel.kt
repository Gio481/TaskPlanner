package com.example.taskplanner.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.usecase.home.HomeUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeVewModel(private val homeUseCase: HomeUseCase) : BaseViewModel() {

    private val _allProjectsLiveData: MutableLiveData<List<ProjectDomain>> = MutableLiveData()
    val allProjectsLiveData: LiveData<List<ProjectDomain>> = _allProjectsLiveData

    private val _getAllTodoProjects: MutableLiveData<Int> = MutableLiveData()
    val getAllTodoProjects: LiveData<Int> = _getAllTodoProjects

    private val _getAllInProgressProjects: MutableLiveData<Int> = MutableLiveData()
    val getAllInProgressProjects: LiveData<Int> = _getAllInProgressProjects

    private val _getAllDoneProjects: MutableLiveData<Int> = MutableLiveData()
    val getAllDoneProjects: LiveData<Int> = _getAllDoneProjects

    private val _userLiveData: MutableLiveData<UserDomain> = MutableLiveData()
    val userLiveData: LiveData<UserDomain> = _userLiveData

    fun getAllProjects() {
        viewModelScope.launch(Dispatchers.IO) {
            _allProjectsLiveData.postValue(homeUseCase.getAllProjects())
        }
    }

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _userLiveData.postValue(homeUseCase.getUserInfo())
        }
    }

    fun gelAllProjectsSize() {
        viewModelScope.launch(Dispatchers.IO) {
            _getAllTodoProjects.postValue(homeUseCase.getProjectsSize(Status.TODO))
            _getAllInProgressProjects.postValue(homeUseCase.getProjectsSize(Status.IN_PROGRESS))
            _getAllDoneProjects.postValue(homeUseCase.getProjectsSize(Status.DONE))
        }
    }

    fun updateUser(fieldName: String, updatedInfo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.updateUser(fieldName, updatedInfo)
        }
    }
}