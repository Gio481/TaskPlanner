package com.example.taskplanner.presentation.ui.auth.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.usecase.auth.signin.SignInUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val signInUseCase: SignInUseCase) : BaseViewModel() {

    private val _signInLiveData: MutableLiveData<AuthResult?> = MutableLiveData()
    val signInLiveData: LiveData<AuthResult?> = _signInLiveData

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _signInLiveData.postValue(signInUseCase.signIn(email, password) { getErrorMessage(it) })
        }
    }
}