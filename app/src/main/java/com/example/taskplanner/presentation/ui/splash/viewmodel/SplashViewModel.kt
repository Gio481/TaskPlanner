package com.example.taskplanner.presentation.ui.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.usecase.splash.IsUserLoggedUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val isUserLoggedUseCase: IsUserLoggedUseCase) : ViewModel() {

    private val _isUserLoggedLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isUserLoggedLiveData: LiveData<Boolean> = _isUserLoggedLiveData

    fun isUserLogged() {
        viewModelScope.launch {
            _isUserLoggedLiveData.postValue(isUserLoggedUseCase.isUserLogged())
        }
    }
}