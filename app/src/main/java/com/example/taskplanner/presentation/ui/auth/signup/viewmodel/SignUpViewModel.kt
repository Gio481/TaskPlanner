package com.example.taskplanner.presentation.ui.auth.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.usecase.auth.signup.SignUpUseCase
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.presentation.base.BaseViewModel
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : BaseViewModel() {

    private val _signUpLiveData: MutableLiveData<AuthResult?> = MutableLiveData()
    val signUpLiveData: LiveData<AuthResult?> = _signUpLiveData

    fun signUp(userDomain: UserDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase.signUp(userDomain)
        }
    }

}