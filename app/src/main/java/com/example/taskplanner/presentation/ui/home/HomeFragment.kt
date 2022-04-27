package com.example.taskplanner.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskplanner.databinding.FragmentHomeBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.home.viewmodel.HomeVewModel
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeVewModel>() {
    override val bindingInflater: (inflater: LayoutInflater, container: ViewGroup?, attachRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun getViewModelClass(): KClass<HomeVewModel> = HomeVewModel::class

    override fun onBindViewModel(viewModel: HomeVewModel) {}
}