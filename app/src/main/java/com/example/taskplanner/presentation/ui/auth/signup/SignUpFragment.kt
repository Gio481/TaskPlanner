package com.example.taskplanner.presentation.ui.auth.signup

import com.example.taskplanner.databinding.FragmentSignUpBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.BindingInflater
import com.example.taskplanner.presentation.ui.auth.signup.viewmodel.SignUpViewModel
import kotlin.reflect.KClass

class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override val inflater: BindingInflater<FragmentSignUpBinding>
        get() = FragmentSignUpBinding::inflate

    override fun getViewModelClass(): KClass<SignUpViewModel> = SignUpViewModel::class

    override fun onBindViewModel(viewModel: SignUpViewModel) {
    }
}