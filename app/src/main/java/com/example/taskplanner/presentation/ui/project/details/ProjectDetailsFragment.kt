package com.example.taskplanner.presentation.ui.project.details

import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskplanner.R
import com.example.taskplanner.databinding.FragmentProjectDetailsBinding
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.project.details.actions.ProjectActionsBottomSheetFragment
import com.example.taskplanner.presentation.ui.project.details.adapter.OnItemClickListener
import com.example.taskplanner.presentation.ui.project.details.adapter.SubTasksAdapter
import com.example.taskplanner.presentation.ui.project.details.viewmodel.ProjectDetailsViewModel
import com.example.taskplanner.util.ActionTypes
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.bundle
import com.example.taskplanner.util.extensions.*
import kotlin.reflect.KClass

class ProjectDetailsFragment :
    BaseFragment<FragmentProjectDetailsBinding, ProjectDetailsViewModel>(), OnItemClickListener {

    override val inflater: BindingInflater<FragmentProjectDetailsBinding>
        get() = FragmentProjectDetailsBinding::inflate

    override fun getViewModelClass(): KClass<ProjectDetailsViewModel> =
        ProjectDetailsViewModel::class

    private val args: ProjectDetailsFragmentArgs by navArgs()
    private val taskAdapter by lazy { SubTasksAdapter(this) }
    private val actionBottomSheet by lazy { ProjectActionsBottomSheetFragment() }

    override fun onBindViewModel(viewModel: ProjectDetailsViewModel) {
        binding.progressBarView.isVisible(true)
        with(args.project) {
            viewModel.getAllSubTasks(projectId)
            viewModel.getDoneTasksPercent(projectId)
            binding.projectEndInTimeTextView.timer(startDate!!, endDate!!)
        }

        taskAdapter.progressListener = { view, taskId ->
            updateSubTaskProgress(view as TextView, taskId, viewModel)
        }
        setUpRecyclerView()
        observeErrorLiveData(viewModel)
        observeSubTasksLiveData(viewModel)
        observeDeleteProjectLiveData(viewModel)
        observeDoneTasksPercentLiveData(viewModel)
        determineBottomSheetAction(viewModel)
        setUpProjectDetailsScreen()
        setListener(viewModel)
    }

    private fun observeDoneTasksPercentLiveData(viewModel: ProjectDetailsViewModel) {
        observer(viewModel.doneTasksPercentLiveData) {
            binding.progressBarView.isVisible(false)
            binding.doneStateLoader.progress = it
        }
    }

    private fun setUpProjectDetailsScreen() {
        with(binding) {
            with(args.project) {
                projectNameEditText.setText(title)
                projectTimeTextView.text = startDate!!.toEndDate(endDate!!)
                projectProgressButton.text = projectProgress?.value
                projectProgressButton.setTextColor(ContextCompat.getColor(requireContext(),
                    projectProgress?.color!!))
                projectEndInTimeTextView.countDownTimer.start()
            }
        }
    }

    private fun updateSubTaskProgress(
        view: TextView,
        taskId: String,
        viewModel: ProjectDetailsViewModel,
    ) {
        inflatePopupMenu(view,
            todoAction = {
                popupMenuItemAction(view, Progress.TODO, taskId, viewModel)
            },
            inProgressAction = {
                popupMenuItemAction(view, Progress.IN_PROGRESS, taskId, viewModel)
            },
            doneAction = {
                popupMenuItemAction(view, Progress.DONE, taskId, viewModel)
            })
    }

    private fun popupMenuItemAction(
        view: TextView,
        progress: Progress,
        taskId: String,
        viewModel: ProjectDetailsViewModel,
    ) {
        binding.progressBarView.isVisible(true)
        with(view) {
            setBackgroundColor(ContextCompat.getColor(requireContext(),
                progress.color))
            text = progress.value
        }
        with(viewModel) {
            getDoneTasksPercent(args.project.projectId)
            updateSubTaskProgress(taskId, progress)
            getAllSubTasks(args.project.projectId)
        }
    }

    private fun observeDeleteProjectLiveData(viewModel: ProjectDetailsViewModel) {
        observer(viewModel.deleteProjectLiveData) {
            binding.progressBarView.isVisible(false)
            findNavController().navigateUp()
        }
    }

    private fun observeSubTasksLiveData(viewModel: ProjectDetailsViewModel) {
        observer(viewModel.getAllSubTasksLiveData) {
            binding.progressBarView.isVisible(false)
            taskAdapter.submitList(it)
        }
    }

    private fun observeErrorLiveData(viewModel: ProjectDetailsViewModel) {
        observer(viewModel.errorLiveData) {
            binding.progressBarView.isVisible(false)
            showToast(it)
        }
    }

    private fun determineBottomSheetAction(viewModel: ProjectDetailsViewModel) {
        actionBottomSheet.action = {
            when (it) {
                is ActionTypes.Delete -> viewModel.deleteProject(args.project.projectId!!)
                is ActionTypes.Create -> createNewsTask()
                is ActionTypes.Update -> {}
            }
        }
    }

    private fun createNewsTask() {
        findNavController().navigate(ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToTaskCreatorFragment(
            args.project))
    }

    private fun setListener(viewModel: ProjectDetailsViewModel) {
        with(binding) {
            projectActionsButton.setOnClickListener {
                actionBottomSheet.setStyle(STYLE_NORMAL, R.style.BottomSheetTheme)
                actionBottomSheet.show(childFragmentManager, null)
            }
            projectProgressButton.setOnClickListener {
                setProgressUpdaterPopupMenu(it as Button, viewModel)
            }
        }
    }

    private fun setProgressUpdaterPopupMenu(view: Button, viewModel: ProjectDetailsViewModel) {
        inflatePopupMenu(view,
            todoAction = {
                updateProjectProgress(view, Progress.TODO, viewModel)
            },
            inProgressAction = {
                updateProjectProgress(view, Progress.IN_PROGRESS, viewModel)
            },
            doneAction = {
                updateProjectProgress(view, Progress.DONE, viewModel)
            }
        )
    }

    private fun updateProjectProgress(
        view: Button,
        progress: Progress,
        viewModel: ProjectDetailsViewModel,
    ) {
        with(view) {
            setTextColor(ContextCompat.getColor(requireContext(), progress.color))
            text = progress.value
            viewModel.updateProjectProgress(args.project.projectId, progress)
        }
    }


    private fun setUpRecyclerView() {
        with(binding.subTasksRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }

    override fun onItemClick(taskDomain: TaskDomain) {
        val bundle = bundle { putParcelable(TASK_DETAILS_ARGS_NAME, taskDomain) }
        findNavController().safeNavigate(
            R.id.projectDetailsFragment,
            R.id.action_projectDetailsFragment_to_taskDetailsFragment,
            bundle)
    }

    companion object {
        private const val TASK_DETAILS_ARGS_NAME = "task"
    }
}