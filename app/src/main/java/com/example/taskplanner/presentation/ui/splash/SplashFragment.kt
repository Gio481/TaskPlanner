package com.example.taskplanner.presentation.ui.splash

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.databinding.FragmentSplashBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.splash.viewmodel.SplashViewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.extensions.observer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val inflater: BindingInflater<FragmentSplashBinding>
        get() = FragmentSplashBinding::inflate

    override fun getViewModelClass(): KClass<SplashViewModel> = SplashViewModel::class

    override fun onBindViewModel(viewModel: SplashViewModel) {
        viewModel.isUserLogged()
        observeUserLoggedLiveData(viewModel)
    }

    private fun observeUserLoggedLiveData(viewModel: SplashViewModel) {
        observer(viewModel.isUserLoggedLiveData) {
            if (it) {
                navigateDirection(R.id.action_splashFragment_to_homeFragment)
            } else {
                navigateDirection(R.id.action_splashFragment_to_signInFragment)
            }
        }
    }

    private fun navigateDirection(directionId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(SPLASH_SCREEN_DELAY)
            findNavController().navigate(directionId)
        }

    }

    companion object {
        private const val SPLASH_SCREEN_DELAY = 6000L
    }
}