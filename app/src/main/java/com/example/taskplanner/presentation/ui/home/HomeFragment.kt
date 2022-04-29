package com.example.taskplanner.presentation.ui.home

import com.example.taskplanner.databinding.FragmentHomeBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.home.viewmodel.HomeVewModel
import com.example.taskplanner.util.BindingInflater
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeVewModel>() {
    override val inflater: BindingInflater<FragmentHomeBinding>
        get() = FragmentHomeBinding::inflate

    override fun getViewModelClass(): KClass<HomeVewModel> = HomeVewModel::class

    override fun onBindViewModel(viewModel: HomeVewModel) {}
}