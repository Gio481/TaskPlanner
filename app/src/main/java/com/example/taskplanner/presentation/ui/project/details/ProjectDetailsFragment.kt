package com.example.taskplanner.presentation.ui.project.details

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskplanner.R
import com.example.taskplanner.databinding.FragmentProjectDetailsBinding
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.project.details.actions.ProjectActionsBottomSheetFragment
import com.example.taskplanner.presentation.ui.project.details.adapter.SubTasksAdapter
import com.example.taskplanner.presentation.ui.project.details.viewmodel.ProjectDetailsViewModel
import com.example.taskplanner.util.ActionTypes
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.bundle
import com.example.taskplanner.util.extensions.*
import kotlin.reflect.KClass

class ProjectDetailsFragment :
    BaseFragment<FragmentProjectDetailsBinding, ProjectDetailsViewModel>() {

    override val inflater: BindingInflater<FragmentProjectDetailsBinding>
        get() = FragmentProjectDetailsBinding::inflate

    override fun getViewModelClass(): KClass<ProjectDetailsViewModel> =
        ProjectDetailsViewModel::class

    private val args: ProjectDetailsFragmentArgs by navArgs()
    private val taskAdapter by lazy { SubTasksAdapter() }
    private val actionBottomSheet by lazy { ProjectActionsBottomSheetFragment() }


    override fun onBindViewModel(viewModel: ProjectDetailsViewModel) {
        binding.progressBarView.isVisible(true)
        setUpProject(viewModel)
        setUpProjectProgressDetails(viewModel)
        observeProjectLiveData(viewModel)
        getSubTasks(viewModel)
        observeSubTasksLiveData(viewModel)
        setSubTaskAdapterListeners(viewModel)
        observeDoneTasksPercentLiveData(viewModel)
        observeDeleteProjectLiveData(viewModel)
        observeErrorLiveData(viewModel)
        determineBottomSheetAction(viewModel)
        setUpdateFieldsCustomViewListeners(viewModel)
        setListener(viewModel)
        setUpRecyclerView()
    }

    private fun setUpProject(viewModel: ProjectDetailsViewModel) {
        with(viewModel) {
            project = args.project
            startDate = project.startDate
            endDate = project.endDate
            getProjectInfo(project)
        }
    }

    private fun setUpProjectProgressDetails(viewModel: ProjectDetailsViewModel) {
        with(binding.projectProgressButton) {
            text = viewModel.project.projectProgress?.value
            setTextColor(ContextCompat.getColor(requireContext(),
                viewModel.project.projectProgress?.color!!))
        }
    }

    private fun setSubTaskAdapterListeners(viewModel: ProjectDetailsViewModel) {
        with(taskAdapter) {
            progressListener = { view, taskId ->
                setSubTaskProgressUpdaterPopupMenu(view as TextView, taskId, viewModel)
            }
            onItemClickListener = {
                val bundle = bundle {
                    putParcelable(TASK_DETAILS_ARGS_NAME, it)
                    putParcelable(PROJECT_DETAILS_ARGS_NAME, viewModel.project)
                }
                navigateTaskDetailsScreen(bundle)
            }
        }
    }

    private fun navigateTaskDetailsScreen(bundle: Bundle) {
        findNavController().safeNavigate(
            R.id.projectDetailsFragment,
            R.id.action_projectDetailsFragment_to_taskDetailsFragment,
            bundle)
    }

    private fun observeProjectLiveData(viewModel: ProjectDetailsViewModel) {
        with(viewModel) {
            observer(projectLiveData) {
                project = it
                setUpProjectDetailsScreen(viewModel, project)
                binding.projectDetailsMotionLayout.transitionEndAction {
                    setUpUpdateFieldsCustomViewFields(viewModel, project)
                }
            }
        }
    }

    private fun observeSubTasksLiveData(viewModel: ProjectDetailsViewModel) {
        observer(viewModel.getAllSubTasksLiveData) {
            binding.progressBarView.isVisible(false)
            taskAdapter.submitList(it)
        }
    }

    private fun observeDoneTasksPercentLiveData(viewModel: ProjectDetailsViewModel) {
        observer(viewModel.doneTasksPercentLiveData) {
            with(binding) {
                progressBarView.isVisible(false)
                doneStateLoader.progress = it
            }
        }
    }

    private fun getSubTasks(viewModel: ProjectDetailsViewModel) {
        with(viewModel) {
            getAllSubTasks()
            getDoneTasksPercent()
        }
    }

    private fun observeDeleteProjectLiveData(viewModel: ProjectDetailsViewModel) {
        observer(viewModel.deleteProjectLiveData) {
            binding.progressBarView.isVisible(false)
            findNavController().navigateUp()
        }
    }


    private fun observeErrorLiveData(viewModel: ProjectDetailsViewModel) {
        observer(viewModel.errorLiveData) {
            binding.progressBarView.isVisible(false)
            showToast(it)
        }
    }

    private fun setUpProjectDetailsScreen(
        viewModel: ProjectDetailsViewModel,
        projectDomain: ProjectDomain,
    ) {
        with(binding) {
            with(projectDomain) {
                projectNameTextView.text = title
                projectTimeTextView.text = startDate!!.toEndDate(endDate!!)
                setUpTimer(this)
                setUpUpdateFieldsCustomViewFields(viewModel, this)
            }
        }
    }

    private fun setUpTimer(project: ProjectDomain) {
        with(binding.projectEndInTimeTextView) {
            timer(project.startDate!!, project.endDate!!)
            if (project.projectProgress == Progress.DONE) {
                setText(getString(R.string.project_is_done_text))
            } else {
                countDownTimer.start()
            }
        }
    }

    private fun setUpUpdateFieldsCustomViewFields(
        viewModel: ProjectDetailsViewModel,
        projectDomain: ProjectDomain,
    ) {
        with(binding.updateFieldsCustomView) {
            with(projectDomain) {
                descriptionText = description
                titleText = title
                dateText = startDate?.toEndDate(endDate!!)
            }
        }
        with(viewModel) {
            startDate = project.startDate
            endDate = project.endDate
        }
    }

    private fun setUpdateFieldsCustomViewListeners(viewModel: ProjectDetailsViewModel) {
        with(binding.updateFieldsCustomView) {
            chooseDateAction = { pickUpDate(viewModel) }
            updateAction = { updateProject(viewModel) }
        }
    }

    private fun pickUpDate(viewModel: ProjectDetailsViewModel) {
        with(viewModel) {
            childFragmentManager.pickDate(startTime = project.startDate,
                endTime = project.endDate) { startingDate, endingDate ->
                binding.updateFieldsCustomView.dateText = startingDate.toEndDate(endingDate)
                startDate = startingDate
                endDate = endingDate
            }
        }
    }

    private fun updateProject(viewModel: ProjectDetailsViewModel) {
        binding.progressBarView.isVisible(true)
        with(viewModel) {
            with(binding.updateFieldsCustomView) {
                if (startDate != project.startDate && endDate != project.endDate) {
                    binding.projectEndInTimeTextView.countDownTimer.cancel()
                }
                updateProject(getItemTitleText(), getItemDescriptionText(), startDate!!, endDate!!)
            }
        }
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

    private fun determineBottomSheetAction(viewModel: ProjectDetailsViewModel) {
        actionBottomSheet.action = {
            with(viewModel) {
                when (it) {
                    is ActionTypes.Delete -> deleteProject()
                    is ActionTypes.Create -> createNewsTask(viewModel)
                }
            }
        }
    }

    private fun createNewsTask(viewModel: ProjectDetailsViewModel) {
        findNavController().navigate(ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToTaskCreatorFragment(
            viewModel.project))
    }

    private fun setProgressUpdaterPopupMenu(view: Button, viewModel: ProjectDetailsViewModel) {
        inflatePopupMenu(view,
            todoAction = {
                nonDoneProjectAction(viewModel)
                updateProjectProgress(view, Progress.TODO, viewModel)

            },
            inProgressAction = {
                nonDoneProjectAction(viewModel)
                updateProjectProgress(view, Progress.IN_PROGRESS, viewModel)

            },
            doneAction = {
                doneProjectAction(viewModel)
                updateProjectProgress(view, Progress.DONE, viewModel)
            }
        )
    }

    private fun nonDoneProjectAction(viewModel: ProjectDetailsViewModel) {
        with(viewModel) {
            with(binding.projectEndInTimeTextView) {
                isFinishedProject = false
                timer(project.startDate!!, project.endDate!!)
                countDownTimer.start()
            }
        }
    }

    private fun doneProjectAction(viewModel: ProjectDetailsViewModel) {
        viewModel.isFinishedProject = true
        with(binding.projectEndInTimeTextView) {
            countDownTimer.cancel()
            setText(getString(R.string.project_is_done_text))
        }
    }

    private fun updateProjectProgress(
        view: Button,
        progress: Progress,
        viewModel: ProjectDetailsViewModel,
    ) {
        with(view) {
            setTextColor(ContextCompat.getColor(requireContext(), progress.color))
            text = progress.value
            viewModel.updateProjectProgress(progress = progress)
        }
    }

    private fun setSubTaskProgressUpdaterPopupMenu(
        view: TextView,
        taskId: String,
        viewModel: ProjectDetailsViewModel,
    ) {
        inflatePopupMenu(view,
            todoAction = {
                updateSubTaskProgress(view, Progress.TODO, taskId, viewModel)
            },
            inProgressAction = {
                updateSubTaskProgress(view, Progress.IN_PROGRESS, taskId, viewModel)
            },
            doneAction = {
                updateSubTaskProgress(view, Progress.DONE, taskId, viewModel)
            })
    }

    private fun updateSubTaskProgress(
        view: TextView,
        progress: Progress,
        taskId: String,
        viewModel: ProjectDetailsViewModel,
    ) {
        binding.progressBarView.isVisible(true)
        with(view) {
            setBackgroundColor(ContextCompat.getColor(requireContext(), progress.color))
            text = progress.value
        }
        with(viewModel) {
            getDoneTasksPercent()
            updateSubTaskProgress(taskId, progress)
            getAllSubTasks()
        }
    }

    private fun setUpRecyclerView() {
        with(binding.subTasksRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }

    companion object {
        private const val TASK_DETAILS_ARGS_NAME = "task"
        private const val PROJECT_DETAILS_ARGS_NAME = "project"
    }
}