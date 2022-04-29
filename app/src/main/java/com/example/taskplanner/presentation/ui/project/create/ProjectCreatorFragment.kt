package com.example.taskplanner.presentation.ui.project.create

import com.example.taskplanner.databinding.FragmentProjectCreatorBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.project.create.viewmodel.ProjectCreatorViewModel
import com.example.taskplanner.util.BindingInflater
import kotlin.reflect.KClass

class ProjectCreatorFragment :
    BaseFragment<FragmentProjectCreatorBinding, ProjectCreatorViewModel>() {

    override val inflater: BindingInflater<FragmentProjectCreatorBinding>
        get() = FragmentProjectCreatorBinding::inflate

    override fun getViewModelClass(): KClass<ProjectCreatorViewModel> =
        ProjectCreatorViewModel::class

    override fun onBindViewModel(viewModel: ProjectCreatorViewModel) {
    }
}