package com.example.taskplanner.presentation.ui.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskplanner.databinding.FragmentSplashBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.splash.viewmodel.SplashViewModel
import kotlin.reflect.KClass

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    override val bindingInflater: (inflater: LayoutInflater, container: ViewGroup?, attachRoot: Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    override fun getViewModelClass(): KClass<SplashViewModel> = SplashViewModel::class

    override fun onBindViewModel(viewModel: SplashViewModel) {
    }
}