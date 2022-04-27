package com.example.taskplanner.presentation.ui.task.create

import com.example.taskplanner.databinding.FragmentTaskCreatorBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.base.BindingInflater
import com.example.taskplanner.presentation.ui.task.create.viewmodel.TaskCreatorViewModel
import kotlin.reflect.KClass

class TaskCreatorFragment : BaseFragment<FragmentTaskCreatorBinding, TaskCreatorViewModel>() {

    override val inflater: BindingInflater<FragmentTaskCreatorBinding>
        get() = FragmentTaskCreatorBinding::inflate

    override fun getViewModelClass(): KClass<TaskCreatorViewModel> = TaskCreatorViewModel::class

    override fun onBindViewModel(viewModel: TaskCreatorViewModel) {}
}