package com.example.taskplanner.presentation.ui.task.create

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.databinding.FragmentTaskCreatorBinding
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.task.create.viewmodel.TaskCreatorViewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.CustomDateValidator
import com.example.taskplanner.util.Status
import com.example.taskplanner.util.extensions.*
import kotlin.reflect.KClass

class TaskCreatorFragment : BaseFragment<FragmentTaskCreatorBinding, TaskCreatorViewModel>() {

    override val inflater: BindingInflater<FragmentTaskCreatorBinding>
        get() = FragmentTaskCreatorBinding::inflate

    override fun getViewModelClass(): KClass<TaskCreatorViewModel> = TaskCreatorViewModel::class

    private var startDate: Long? = null
    private var endDate: Long? = null
    private var task: TaskDomain = TaskDomain()
    private val args: TaskCreatorFragmentArgs by navArgs()

    override fun onBindViewModel(viewModel: TaskCreatorViewModel) {
        observeTaskLiveData(viewModel)
        observeErrorLiveData(viewModel)
        setListeners(viewModel)
    }

    private fun observeTaskLiveData(viewModel: TaskCreatorViewModel) {
        observer(viewModel.taskLiveData) {
            binding.progressBarView.isVisible(false)
            findNavController().navigate(TaskCreatorFragmentDirections.actionTaskCreatorFragmentToTaskDetailsFragment(
                it))
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
                pickUpDate()
            }
            createNewTaskButton.setOnClickListener {
                createNewTask(viewModel)
            }
        }
    }

    private fun pickUpDate() {
        childFragmentManager.pickDate(validator = CustomDateValidator(args.project.startDate,
            args.project.endDate)) { startingDate, endingDate ->
            binding.taskTimeTextView.text = startingDate.toEndDate(endingDate)
            startDate = startingDate
            endDate = endingDate
        }
    }

    private fun createNewTask(viewModel: TaskCreatorViewModel) {
        task = TaskDomain(
            title = binding.taskNameEditText.text.toString(),
            description = binding.taskDescriptionEditText.text.toString(),
            startDate = startDate,
            endDate = endDate,
            taskProgress = Status.TODO
        )
        binding.progressBarView.isVisible(true)
        viewModel.createTask(task, args.project.projectId)
    }
}