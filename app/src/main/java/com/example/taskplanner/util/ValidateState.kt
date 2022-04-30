package com.example.taskplanner.util

sealed class ValidateState {
    object Success : ValidateState()
    data class Error(val message: String) : ValidateState()
}