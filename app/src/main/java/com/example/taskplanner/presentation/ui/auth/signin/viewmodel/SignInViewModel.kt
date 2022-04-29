package com.example.taskplanner.presentation.ui.auth.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.usecase.auth.signin.SignInUseCase
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _signInLiveData: MutableLiveData<AuthResult?> = MutableLiveData()
    val signInLiveData: LiveData<AuthResult?> = _signInLiveData

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _signInLiveData.postValue(signInUseCase.signIn(email, password))
        }
    }
}