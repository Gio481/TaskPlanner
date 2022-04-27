package com.example.taskplanner.presentation.ui.signin

import com.example.taskplanner.databinding.FragmentSignInBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.BindingInflater
import com.example.taskplanner.presentation.ui.signin.viewmodel.SignInViewModel
import kotlin.reflect.KClass

class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {

    override val inflater: BindingInflater<FragmentSignInBinding>
        get() = FragmentSignInBinding::inflate

    override fun getViewModelClass(): KClass<SignInViewModel> = SignInViewModel::class

    override fun onBindViewModel(viewModel: SignInViewModel) {}

}