package com.example.taskplanner.presentation.ui.auth.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.usecase.auth.signup.SignUpUseCase
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel(), GetErrorMessage {

    private val _errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val errorMessageLiveData: LiveData<String> = _errorMessageLiveData

    private val _signUpLiveData: MutableLiveData<AuthResult?> = MutableLiveData()
    val signUpLiveData: LiveData<AuthResult?> = _signUpLiveData

    override fun errorMessage(message: String) {
        _errorMessageLiveData.postValue(message)
    }

    fun signUp(userDomain: UserDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase.signUp(userDomain)
        }
    }

}