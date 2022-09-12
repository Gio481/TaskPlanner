package com.example.taskplanner.presentation.ui.auth.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.domain.usecase.auth.signup.SignUpUseCase
import com.example.taskplanner.presentation.base.BaseViewModel
import com.example.taskplanner.presentation.ui.auth.signup.validator.SignUpValidator
import com.example.taskplanner.util.ValidateState
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val validator: SignUpValidator,
) : BaseViewModel() {

    private val _signUpLiveData: MutableLiveData<AuthResult?> = MutableLiveData()
    val signUpLiveData: LiveData<AuthResult?> = _signUpLiveData

    fun signUp(userDomain: UserDomain, repeatPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val validation = validator.validate(userDomain, repeatPassword)) {
                is ValidateState.Error -> getErrorMessage(validation.message)
                is ValidateState.Success -> _signUpLiveData.postValue(signUpUseCase.signUp(
                    userDomain) { getErrorMessage(it) })
            }
        }
    }
}