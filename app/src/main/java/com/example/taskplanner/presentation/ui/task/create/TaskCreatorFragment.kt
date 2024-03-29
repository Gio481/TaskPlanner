package com.example.taskplanner.presentation.ui.task.create

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.databinding.FragmentTaskCreatorBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.task.create.viewmodel.TaskCreatorViewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.CustomDateValidator
import com.example.taskplanner.util.extensions.*
import kotlin.reflect.KClass

class TaskCreatorFragment : BaseFragment<FragmentTaskCreatorBinding, TaskCreatorViewModel>() {

    override val inflater: BindingInflater<FragmentTaskCreatorBinding>
        get() = FragmentTaskCreatorBinding::inflate

    override fun getViewModelClass(): KClass<TaskCreatorViewModel> = TaskCreatorViewModel::class

    private val args: TaskCreatorFragmentArgs by navArgs()

    override fun onBindViewModel(viewModel: TaskCreatorViewModel) {
        getTaskOwnerProjectInfo(viewModel)
        observeTaskOwnerProjectLiveData(viewModel)
        observeTaskLiveData(viewModel)
        observeErrorLiveData(viewModel)
        setListeners(viewModel)
    }

    private fun getTaskOwnerProjectInfo(viewModel: TaskCreatorViewModel) {
        viewModel.getTaskOwnerProjectInfo(args.project)
    }

    private fun observeTaskOwnerProjectLiveData(viewModel: TaskCreatorViewModel) {
        with(viewModel) {
            observer(taskOwnerProjectLiveData) {
                project = it
            }
        }
    }

    private fun observeTaskLiveData(viewModel: TaskCreatorViewModel) {
        observer(viewModel.taskLiveData) {
            binding.progressBarView.isVisible(false)
            findNavController().navigate(TaskCreatorFragmentDirections.actionTaskCreatorFragmentToTaskDetailsFragment(
                it, viewModel.project))
        }
    }

    private fun observeErrorLiveData(viewModel: TaskCreatorViewModel) {
        observer(viewModel.errorLiveData) {
            binding.progressBarView.isVisible(false)
            showToast(it)
        }
    }

    private fun setListeners(viewModel: TaskCreatorViewModel) {
        with(binding) {
            datePickerButton.setOnClickListener {
                pickUpDate(viewModel)
            }
            createNewTaskButton.setOnClickListener {
                createNewTask(viewModel)
            }
        }
    }

    private fun pickUpDate(viewModel: TaskCreatorViewModel) {
        with(viewModel) {
            childFragmentManager.pickDate(validator = CustomDateValidator(project.startDate,
                project.endDate)) { startingDate, endingDate ->
                binding.taskTimeTextView.text = startingDate.toEndDate(endingDate)
                startDate = startingDate
                endDate = endingDate
            }
        }
    }

    private fun createNewTask(viewModel: TaskCreatorViewModel) {
        with(viewModel) {
            val newTask = newTask(
                title = binding.taskNameEditText.text.toString(),
                description = binding.taskDescriptionEditText.text.toString(),
            )
            binding.progressBarView.isVisible(true)
            createTask(newTask)
        }
    }
}