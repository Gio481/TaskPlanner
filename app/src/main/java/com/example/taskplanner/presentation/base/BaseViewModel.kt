package com.example.taskplanner.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _successLiveData: MutableLiveData<Unit> = MutableLiveData()
    val successLiveData: LiveData<Unit> = _successLiveData

    protected fun getErrorMessage(message: String) {
        _errorLiveData.postValue(message)
    }

    protected fun successData(value: Unit) {
        _successLiveData.postValue(value)
    }
}