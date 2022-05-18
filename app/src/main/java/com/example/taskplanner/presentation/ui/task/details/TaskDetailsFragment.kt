package com.example.taskplanner.presentation.ui.task.details

import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.databinding.FragmentTaskDetailsBinding
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.task.details.viewmodel.TaskDetailsViewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.Status
import com.example.taskplanner.util.extensions.*
import kotlin.reflect.KClass

class TaskDetailsFragment : BaseFragment<FragmentTaskDetailsBinding, TaskDetailsViewModel>() {

    override val inflater: BindingInflater<FragmentTaskDetailsBinding>
        get() = FragmentTaskDetailsBinding::inflate

    override fun getViewModelClass(): KClass<TaskDetailsViewModel> = TaskDetailsViewModel::class

    private val args: TaskDetailsFragmentArgs by navArgs()

    override fun onBindViewModel(viewModel: TaskDetailsViewModel) {
        setUpTaskDetailsScreen()
        observeDeleteTaskLiveData(viewModel)
        observeErrorLiveData(viewModel)
        setListener(viewModel)
    }

    private fun setUpTaskDetailsScreen() {
        with(binding) {
            with(args.task) {
                taskNameEditText.setText(title)
                taskDescriptionEditText.setText(description)
                taskTimeTextView.text = startDate!!.toEndDate(endDate!!)
                taskEndInTimerCustomView.timer(startDate, endDate)
                taskStateButton.text = getString(taskProgress?.value!!)
                taskStateButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
                    taskProgress.color))
                taskEndInTimerCustomView.startTimer()
            }
        }
    }

    private fun observeDeleteTaskLiveData(viewModel: TaskDetailsViewModel) {
        observer(viewModel.successLiveData) {
            binding.progressBarView.isVisible(false)
            findNavController().navigateUp()
        }
    }

    private fun observeErrorLiveData(viewModel: TaskDetailsViewModel) {
        observer(viewModel.errorLiveData) {
            binding.progressBarView.isVisible(false)
            showToast(it)
        }
    }


    private fun setListener(viewModel: TaskDetailsViewModel) {
        with(binding) {
            taskStateButton.setOnClickListener {
                setProgressUpdaterPopupMenu(it as Button, viewModel)
            }
            deleteTaskActionButton.setOnClickListener {
                binding.progressBarView.isVisible(true)
                viewModel.deleteTask(args.task.taskId)
            }
        }
    }

    private fun setProgressUpdaterPopupMenu(view: Button, viewModel: TaskDetailsViewModel) {
        inflatePopupMenu(view,
            todoAction = {
                updateTaskProgress(view, Status.TODO, viewModel)
            },
            inProgressAction = {
                updateTaskProgress(view, Status.IN_PROGRESS, viewModel)
            },
            doneAction = {
                updateTaskProgress(view, Status.DONE, viewModel)
            }
        )
    }

    private fun updateTaskProgress(
        view: Button,
        progress: Status,
        viewModel: TaskDetailsViewModel,
    ) {
        with(view) {
            setBackgroundColor(ContextCompat.getColor(requireContext(), progress.color))
            text = getString(progress.value)
        }
        viewModel.updateTaskProgress(args.task.taskId, progress)
    }
}