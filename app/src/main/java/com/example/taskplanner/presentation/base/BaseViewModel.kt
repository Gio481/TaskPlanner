package com.example.taskplanner.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskplanner.domain.usecase.util.GetErrorMessage

abstract class BaseViewModel : ViewModel(), GetErrorMessage {

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = _errorLiveData

    override fun errorMessage(message: String) {
        _errorLiveData.postValue(message)
    }
}