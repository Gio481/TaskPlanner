package com.example.taskplanner.presentation.ui.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskplanner.databinding.FragmentSignUpBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.signup.viewmodel.SignUpViewModel
import kotlin.reflect.KClass

class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {
    override val bindingInflater: (inflater: LayoutInflater, container: ViewGroup?, attachRoot: Boolean) -> FragmentSignUpBinding
        get() = FragmentSignUpBinding::inflate

    override fun getViewModelClass(): KClass<SignUpViewModel> = SignUpViewModel::class

    override fun onBindViewModel(viewModel: SignUpViewModel) {
    }
}