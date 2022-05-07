package com.example.taskplanner.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.usecase.home.project.GetProjectsUseCase
import com.example.taskplanner.domain.usecase.home.user.UserUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.util.Progress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeVewModel(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val userUseCase: UserUseCase,
) : BaseViewModel() {

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
            _allProjectsLiveData.postValue(getProjectsUseCase.getAllProjects { getErrorMessage(it) })
        }
    }

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _userLiveData.postValue(userUseCase.getUserInfo { getErrorMessage(it) })
        }
    }

    fun getAllProjectsSize() {
        viewModelScope.launch(Dispatchers.IO) {
            _getAllTodoProjects.postValue(getProjectsUseCase.getProjectsSize(Progress.TODO) {
                getErrorMessage(it)
            })
            _getAllInProgressProjects.postValue(getProjectsUseCase.getProjectsSize(Progress.IN_PROGRESS) {
                getErrorMessage(it)
            })
            _getAllDoneProjects.postValue(getProjectsUseCase.getProjectsSize(Progress.DONE) {
                getErrorMessage(it)
            })
        }
    }

    fun updateUser(userDomain: UserDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.updateUser(userDomain) { getErrorMessage(it) }
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            successData(userUseCase.logOut())
        }
    }

}