package com.example.taskplanner.presentation.ui.project.create

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskplanner.databinding.FragmentProjectCreatorBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.project.create.viewmodel.ProjectCreatorViewModel
import kotlin.reflect.KClass

class ProjectCreatorFragment :
    BaseFragment<FragmentProjectCreatorBinding, ProjectCreatorViewModel>() {
    override val bindingInflater: (inflater: LayoutInflater, container: ViewGroup?, attachRoot: Boolean) -> FragmentProjectCreatorBinding
        get() = FragmentProjectCreatorBinding::inflate

    override fun getViewModelClass(): KClass<ProjectCreatorViewModel> =
        ProjectCreatorViewModel::class

    override fun onBindViewModel(viewModel: ProjectCreatorViewModel) {
    }
}