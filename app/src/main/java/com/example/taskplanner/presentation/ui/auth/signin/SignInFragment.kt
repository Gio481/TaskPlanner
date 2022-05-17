package com.example.taskplanner.presentation.ui.auth.signin

import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.databinding.FragmentSignInBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.auth.signin.viewmodel.SignInViewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.extensions.isVisible
import com.example.taskplanner.util.extensions.observer
import com.example.taskplanner.util.extensions.showToast
import kotlin.reflect.KClass

class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {

    override val inflater: BindingInflater<FragmentSignInBinding>
        get() = FragmentSignInBinding::inflate

    override fun getViewModelClass(): KClass<SignInViewModel> = SignInViewModel::class

    override fun onBindViewModel(viewModel: SignInViewModel) {
        binding.passwordEditTextLayout.setPasswordToggleButton()
        observeAuthResultLiveData(viewModel)
        observeErrorLiveData(viewModel)
        setListeners(viewModel)
    }

    private fun observeAuthResultLiveData(viewModel: SignInViewModel) {
        observer(viewModel.signInLiveData) {
            binding.progressBarView.isVisible(false)
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }
    }

    private fun observeErrorLiveData(viewModel: SignInViewModel) {
        observer(viewModel.errorLiveData) {
            binding.progressBarView.isVisible(false)
            showToast(it)
        }
    }

    private fun setListeners(viewModel: SignInViewModel) {
        with(binding) {
            signInButton.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
            logInButton.setOnClickListener {
                progressBarView.isVisible(true)
                viewModel.signIn(emailEditTextLayout.getText(), passwordEditTextLayout.getText())
            }
        }
    }
}