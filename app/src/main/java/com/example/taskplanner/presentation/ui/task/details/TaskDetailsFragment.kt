package com.example.taskplanner.presentation.ui.task.details

import android.app.Dialog
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.R
import com.example.taskplanner.databinding.FragmentTaskDetailsBinding
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.task.details.viewmodel.TaskDetailsViewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.Constants.TIMER_STOP_AND_START_DELAY
import com.example.taskplanner.util.CustomDateValidator
import com.example.taskplanner.util.Status
import com.example.taskplanner.util.extensions.*
import kotlinx.coroutines.delay
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
        binding.taskEndInTimerCustomView.timer(viewModel.task.startDate!!, viewModel.task.endDate!!)
        observeProjectDateLiveData(viewModel)
        configureDialog(viewModel)
        setUpInvalidDateWarningDialog(viewModel)
        setUpTaskProgressDetails(viewModel)
        observeTaskLiveData(viewModel)
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
            projectStartDate = args.project.startDate
            projectEndDate = args.project.endDate
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
        if (isDateInvalid(viewModel)) dialog?.show()
    }

    private fun setUpTaskProgressDetails(viewModel: TaskDetailsViewModel) {
        with(binding.taskStateButton) {
            with(viewModel.task.taskProgress!!) {
                text = getString(value)
                setBackgroundColor(requireContext(), color)
            }
        }
    }

    private fun observeTaskLiveData(viewModel: TaskDetailsViewModel) {
        with(viewModel) {
            observer(viewModel.taskLiveData) {
                task = it
                setUpTaskDetailsScreen(viewModel, task)
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
                setUpTimer(viewModel, this)
                setUpFieldCustomViewFields(viewModel, this)
            }
        }
    }

    private fun setUpTimer(viewModel: TaskDetailsViewModel, task: TaskDomain) {
        launchScope {
            with(binding.taskEndInTimerCustomView) {
                timer(task.startDate!!, task.endDate!!)
                when {
                    isTaskDone(task) -> {
                        cancelTimer()
                        setText(getString(R.string.task_is_done_text))
                    }
                    isDateInvalid(viewModel) -> {
                        startTimer()
                        setText(getString(R.string.invalid_date_text))
                    }
                    else -> {
                        delay(TIMER_STOP_AND_START_DELAY)
                        startTimer()
                    }
                }
            }
        }
    }

    private fun isTaskDone(taskDomain: TaskDomain): Boolean {
        return taskDomain.taskProgress == Status.DONE
    }

    private fun isDateInvalid(viewModel: TaskDetailsViewModel): Boolean {
        return with(viewModel) {
            !(task.startDate.isValidateWith(projectStartDate, projectEndDate) ||
                    task.endDate.isValidateWith(projectStartDate, projectEndDate))
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
                    binding.taskEndInTimerCustomView.startTimer()
                }
                updateTask(getItemTitleText(), getItemDescriptionText())
            }
        }
    }

    private fun setUpFieldCustomViewFields(
        viewModel: TaskDetailsViewModel,
        taskDomain: TaskDomain,
    ) {
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
                progressBarView.isVisible(true)
                viewModel.deleteTask()
            }
            taskDetailsMotionLayout.transitionEndAction {
                setUpFieldCustomViewFields(viewModel, viewModel.task)
            }
        }
    }

    private fun updateDate(viewModel: TaskDetailsViewModel) {
        if (isDateChosen) {
            viewModel.updateTask()
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
                launchScope {
                    nonDoneTaskAction(viewModel)
                    updateTaskProgress(view, Status.TODO, viewModel)
                }
            },
            inProgressAction = {
                launchScope {
                    nonDoneTaskAction(viewModel)
                    updateTaskProgress(view, Status.IN_PROGRESS, viewModel)
                }
            },
            doneAction = {
                launchScope {
                    doneTaskAction(viewModel)
                    updateTaskProgress(view, Status.DONE, viewModel)
                }
            }
        )
    }

    private suspend fun nonDoneTaskAction(viewModel: TaskDetailsViewModel) {
        with(viewModel) {
            with(binding.taskEndInTimerCustomView) {
                isFinishedTask = false
                delay(TIMER_STOP_AND_START_DELAY)
                timer(task.startDate!!, task.endDate!!)
                startTimer()
            }
        }
    }

    private suspend fun doneTaskAction(viewModel: TaskDetailsViewModel) {
        viewModel.isFinishedTask = true
        with(binding.taskEndInTimerCustomView) {
            delay(TIMER_STOP_AND_START_DELAY)
            cancelTimer()
            setText(getString(R.string.task_is_done_text))

        }
    }

    private fun updateTaskProgress(
        view: Button,
        progress: Status,
        viewModel: TaskDetailsViewModel,
    ) {
        with(view) {
            setBackgroundColor(requireContext(), progress.color)
            text = getString(progress.value)
        }
        viewModel.updateTaskProgress(progress)
    }
}