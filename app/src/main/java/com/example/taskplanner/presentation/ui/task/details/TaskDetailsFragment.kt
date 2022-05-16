package com.example.taskplanner.presentation.ui.task.details

import android.app.Dialog
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.R
import com.example.taskplanner.databinding.FragmentTaskDetailsBinding
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.task.details.viewmodel.TaskDetailsViewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.CustomDateValidator
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.extensions.*
import kotlin.reflect.KClass

class TaskDetailsFragment : BaseFragment<FragmentTaskDetailsBinding, TaskDetailsViewModel>() {

    override val inflater: BindingInflater<FragmentTaskDetailsBinding>
        get() = FragmentTaskDetailsBinding::inflate

    override fun getViewModelClass(): KClass<TaskDetailsViewModel> = TaskDetailsViewModel::class

    private val args: TaskDetailsFragmentArgs by navArgs()
    private var isDateChosen = false
    private var dialog: Dialog? = null

    override fun onBindViewModel(viewModel: TaskDetailsViewModel) {
        setUpTask(viewModel)
        observeProjectDateLiveData(viewModel)
        setUpTaskProgressDetails(viewModel)
        observeTaskLiveData(viewModel)
        configureDialog(viewModel)
        setUpInvalidDateWarningDialog(viewModel)
        observeDeleteTaskLiveData(viewModel)
        observeErrorLiveData(viewModel)
        setUpUpdateFieldsCustomViewActions(viewModel)
        setListener(viewModel)
    }

    private fun setUpTask(viewModel: TaskDetailsViewModel) {
        with(viewModel) {
            task = args.task
            startDate = task.startDate
            endDate = task.endDate
            getTaskInfo(task)
            getProjectDate(args.project)
        }
    }

    private fun observeProjectDateLiveData(viewModel: TaskDetailsViewModel) {
        with(viewModel) {
            observer(projectDateLiveData) {
                projectStartDate = it.startDate
                projectEndDate = it.endDate
            }
        }
    }

    private fun setUpInvalidDateWarningDialog(viewModel: TaskDetailsViewModel) {
        with(viewModel) {
            if (!(task.startDate!!.isValidateWith(startDate!!,
                    endDate!!) && task.endDate!!.isValidateWith(startDate!!, endDate!!))
            ) {
                binding.taskEndInTimeTextView.countDownTimer.cancel()
                binding.taskEndInTimeTextView.setText(getString(R.string.invalid_date_text))
                dialog?.show()
            }
        }
    }

    private fun setUpTaskProgressDetails(viewModel: TaskDetailsViewModel) {
        with(binding.taskStateButton) {
            text = viewModel.task.taskProgress?.value
            setBackgroundColor(ContextCompat.getColor(requireContext(),
                viewModel.task.taskProgress?.color!!))
        }
    }

    private fun observeTaskLiveData(viewModel: TaskDetailsViewModel) {
        with(viewModel) {
            observer(viewModel.taskLiveData) {
                task = it
                setUpTaskDetailsScreen(viewModel, task)
                binding.taskDetailsMotionLayout.transitionEndAction {
                    setUpTaskDetailsScreen(viewModel, task)
                }
            }
        }
    }

    private fun configureDialog(viewModel: TaskDetailsViewModel) {
        dialog = requireContext().createDialog(
            updateTaskAction = { updateDate(viewModel) },
            deleteTaskAction = { viewModel.deleteTask() },
            chooseDateAction = { pickUpDate(viewModel) })
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

    private fun setUpTaskDetailsScreen(viewModel: TaskDetailsViewModel, taskDomain: TaskDomain) {
        with(binding) {
            with(taskDomain) {
                taskNameEditText.text = title
                taskDescriptionEditText.text = description
                taskTimeTextView.text = startDate!!.toEndDate(endDate!!)
                setUpTimer(this)
                setUpFieldCustomViewFields(viewModel, this)
            }
        }
    }

    private fun setUpTimer(task: TaskDomain) {
        with(binding.taskEndInTimeTextView) {
            timer(task.startDate!!, task.endDate!!)
            if (task.taskProgress == Progress.DONE) {
                setText(getString(R.string.task_is_done_text))
            } else {
                countDownTimer.start()
            }
        }
    }

    private fun setUpUpdateFieldsCustomViewActions(viewModel: TaskDetailsViewModel) {
        with(binding.updateFieldsCustomView) {
            chooseDateAction = { pickUpDate(viewModel) }
            updateAction = { updateTask(viewModel) }
        }
    }

    private fun updateTask(viewModel: TaskDetailsViewModel) {
        binding.progressBarView.isVisible(true)
        with(viewModel) {
            with(binding.updateFieldsCustomView) {
                if (startDate != task.startDate || endDate != task.endDate) {
                    binding.taskEndInTimeTextView.countDownTimer.cancel()
                }
                updateTask(getItemTitleText(), getItemDescriptionText(), startDate!!, endDate!!)
            }
        }
    }

    private fun setUpFieldCustomViewFields(viewModel: TaskDetailsViewModel, taskDomain: TaskDomain, ) {
        with(binding.updateFieldsCustomView) {
            with(taskDomain) {
                descriptionText = description
                titleText = title
                dateText = startDate?.toEndDate(endDate!!)
            }
        }
        with(viewModel) {
            startDate = task.startDate
            endDate = task.endDate
        }
    }

    private fun setListener(viewModel: TaskDetailsViewModel) {
        with(binding) {
            taskStateButton.setOnClickListener {
                setProgressUpdaterPopupMenu(it as Button, viewModel)
            }
            deleteTaskActionButton.setOnClickListener {
                binding.progressBarView.isVisible(true)
                viewModel.deleteTask()
            }
        }
    }

    private fun updateDate(viewModel: TaskDetailsViewModel) {
        if (isDateChosen) {
            viewModel.updateTask(startDate = viewModel.startDate!!, endDate = viewModel.endDate!!)
            dialog?.dismiss()
        } else {
            showToast(getString(R.string.pick_date_error_text))
        }
    }

    private fun pickUpDate(viewModel: TaskDetailsViewModel) {
        with(viewModel) {
            childFragmentManager.pickDate(
                startTime = task.startDate,
                endTime = task.endDate,
                validator = CustomDateValidator(projectStartDate,
                    projectEndDate)) { startingDate, endingDate ->
                binding.updateFieldsCustomView.dateText = startingDate.toEndDate(endingDate)
                startDate = startingDate
                endDate = endingDate
                isDateChosen = true
            }
        }
    }

    private fun setProgressUpdaterPopupMenu(view: Button, viewModel: TaskDetailsViewModel) {
        inflatePopupMenu(view,
            todoAction = {
                nonDoneTaskAction(viewModel)
                updateTaskProgress(view, Progress.TODO, viewModel)
            },
            inProgressAction = {
                nonDoneTaskAction(viewModel)
                updateTaskProgress(view, Progress.IN_PROGRESS, viewModel)
            },
            doneAction = {
                doneTaskAction(viewModel)
                updateTaskProgress(view, Progress.DONE, viewModel)
            }
        )
    }

    private fun nonDoneTaskAction(viewModel: TaskDetailsViewModel) {
        with(viewModel) {
            with(binding.taskEndInTimeTextView) {
                isFinishedTask = false
                timer(task.startDate!!, task.endDate!!)
                countDownTimer.start()
            }
        }
    }

    private fun doneTaskAction(viewModel: TaskDetailsViewModel) {
        viewModel.isFinishedTask = true
        with(binding.taskEndInTimeTextView) {
            countDownTimer.cancel()
            setText(getString(R.string.task_is_done_text))
        }
    }

    private fun updateTaskProgress(
        view: Button,
        progress: Progress,
        viewModel: TaskDetailsViewModel,
    ) {
        with(view) {
            setBackgroundColor(ContextCompat.getColor(requireContext(), progress.color))
            text = progress.value
        }
        viewModel.updateTaskProgress(progress)
    }
}