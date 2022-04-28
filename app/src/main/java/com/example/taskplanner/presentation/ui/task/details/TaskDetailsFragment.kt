package com.example.taskplanner.presentation.ui.task.details

import com.example.taskplanner.databinding.FragmentTaskDetailsBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.task.details.viewmodel.TaskDetailsViewModel
import com.example.taskplanner.util.BindingInflater
import kotlin.reflect.KClass

class TaskDetailsFragment : BaseFragment<FragmentTaskDetailsBinding, TaskDetailsViewModel>() {

    override val inflater: BindingInflater<FragmentTaskDetailsBinding>
        get() = FragmentTaskDetailsBinding::inflate

    override fun getViewModelClass(): KClass<TaskDetailsViewModel> = TaskDetailsViewModel::class

    override fun onBindViewModel(viewModel: TaskDetailsViewModel) {}
}