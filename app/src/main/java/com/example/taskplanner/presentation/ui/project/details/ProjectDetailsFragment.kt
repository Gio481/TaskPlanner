package com.example.taskplanner.presentation.ui.project.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskplanner.databinding.FragmentProjectDetailsBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.project.details.viewmodel.ProjectDetailsViewModel
import kotlin.reflect.KClass

class ProjectDetailsFragment :
    BaseFragment<FragmentProjectDetailsBinding, ProjectDetailsViewModel>() {
    override val bindingInflater: (inflater: LayoutInflater, container: ViewGroup?, attachRoot: Boolean) -> FragmentProjectDetailsBinding
        get() = FragmentProjectDetailsBinding::inflate

    override fun getViewModelClass(): KClass<ProjectDetailsViewModel> =
        ProjectDetailsViewModel::class

    override fun onBindViewModel(viewModel: ProjectDetailsViewModel) {
    }
}