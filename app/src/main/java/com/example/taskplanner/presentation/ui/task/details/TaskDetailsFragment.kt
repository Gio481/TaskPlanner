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
    private var startDate: Long? = null
    private var endDate: Long? = null
    private var task = TaskDomain()
    private var isDateChosen = false
    private var dialog: Dialog? = null

    override fun onBindViewModel(viewModel: TaskDetailsViewModel) {
        binding.taskEndInTimeTextView.timer(args.task.startDate!!, args.task.endDate!!)
        setUpDialog(viewModel)
        setUpInvalidDateWarningDialog()
        observeDeleteTaskLiveData(viewModel)
        observeErrorLiveData(viewModel)
        setUpTaskDetailsScreen()
        setUpUpdateFieldsCustomViewActions(viewModel)
        observeUpdatedTaskLiveData(viewModel)
        setListener(viewModel)
        task = args.task
    }

    private fun setUpDialog(viewModel: TaskDetailsViewModel) {
        dialog = requireContext().createDialog(
            updateTaskAction = { updateDate(viewModel) },
            deleteTaskAction = { viewModel.deleteTask(args.task.taskId) },
            chooseDateAction = { pickUpDate() })
    }

    private fun setUpInvalidDateWarningDialog() {
        with(args) {
            if (!(task.startDate!!.isValidateWith(project.startDate!!,
                    project.endDate!!) && task.endDate!!.isValidateWith(project.startDate!!,
                    project.endDate!!))
            ) {
                binding.taskEndInTimeTextView.countDownTimer.cancel()
                dialog?.show()
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

    private fun setUpTaskDetailsScreen() {
        with(binding) {
            with(args.task) {
                taskNameEditText.setText(title)
                taskDescriptionEditText.text = description
                taskTimeTextView.text = startDate!!.toEndDate(endDate!!)
                taskEndInTimeTextView.countDownTimer.start()
                taskStateButton.text = taskProgress?.value
                taskStateButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
                    taskProgress?.color!!))
                setUpFieldCustomViewFields(this)
            }
        }
    }

    private fun setUpUpdateFieldsCustomViewActions(viewModel: TaskDetailsViewModel) {
        with(binding.updateFieldsCustomView) {
            chooseDateAction = { pickUpDate() }
            updateAction = { updateTask(viewModel) }
        }
    }

    private fun updateTask(viewModel: TaskDetailsViewModel) {
        val task = TaskDomain(
            title = binding.updateFieldsCustomView.getItemTitleText(),
            description = binding.updateFieldsCustomView.getItemDescriptionText(),
            endDate = endDate,
            startDate = startDate
        )
        binding.progressBarView.isVisible(true)
        viewModel.updateTask(args.task.taskId!!, task)
    }

    private fun observeUpdatedTaskLiveData(viewModel: TaskDetailsViewModel) {
        observer(viewModel.updatedTaskLiveData) {
            with(binding) {
                taskDescriptionEditText.text = it.description
                taskNameEditText.setText(it.title)
                taskTimeTextView.text = it.startDate?.toEndDate(it.endDate!!)
                with(taskEndInTimeTextView) {
                    countDownTimer.cancel()
                    timer(it.startDate!!, it.endDate!!)
                    countDownTimer.start()
                }
                taskDetailsMotionLayout.transitionEndAction {
                    setUpFieldCustomViewFields(it)
                }
            }
        }
    }

    private fun setUpFieldCustomViewFields(taskDomain: TaskDomain) {
        with(binding.updateFieldsCustomView) {
            with(taskDomain) {
                descriptionText = description
                titleText = title
                dateText = startDate?.toEndDate(endDate!!)
            }
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

    private fun updateDate(viewModel: TaskDetailsViewModel) {
        if (isDateChosen) {
            val task = TaskDomain(startDate = startDate, endDate = endDate)
            viewModel.updateTask(args.task.taskId!!, task)
            dialog?.dismiss()
        } else {
            showToast(getString(R.string.pick_date_error_text))
        }
    }

    private fun pickUpDate() {
        with(args) {
            childFragmentManager.pickDate(
                startTime = task.startDate,
                endTime = task.endDate,
                validator = CustomDateValidator(project.startDate,
                    project.endDate)) { startingDate, endingDate ->
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
                updateTaskProgress(view, Progress.TODO, viewModel)
            },
            inProgressAction = {
                updateTaskProgress(view, Progress.IN_PROGRESS, viewModel)
            },
            doneAction = {
                updateTaskProgress(view, Progress.DONE, viewModel)
            }
        )
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
        viewModel.updateTaskProgress(args.task.taskId, progress)
    }
}