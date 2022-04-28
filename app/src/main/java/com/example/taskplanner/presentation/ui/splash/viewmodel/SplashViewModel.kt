package com.example.taskplanner.presentation.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    fun splashAction(action: () -> Unit) {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DELAY)
            action()
        }
    }

    companion object {
        private const val SPLASH_SCREEN_DELAY = 6000L
    }
}