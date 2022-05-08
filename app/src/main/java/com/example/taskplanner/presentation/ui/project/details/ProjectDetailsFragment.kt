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
import com.example.taskplanner.domain.model.ProjectDomain
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
    private var startDate: Long? = null
    private var endDate: Long? = null
    private var project = ProjectDomain()

    override fun onBindViewModel(viewModel: ProjectDetailsViewModel) {
        project = args.project
        binding.progressBarView.isVisible(true)
        taskAdapter.progressListener = { view, taskId ->
            setSubTaskProgressUpdaterPopupMenu(view as TextView, taskId, viewModel)
        }
        observeSubTasksLiveData(viewModel)
        observeDoneTasksPercentLiveData(viewModel)
        getSubTasks(viewModel)
        observeDeleteProjectLiveData(viewModel)
        observeErrorLiveData(viewModel)
        setUpProjectDetailsScreen()
        determineBottomSheetAction(viewModel)
        setUpUpdateFieldsCustomView(viewModel)
        observeUpdatedProjectLiveData(viewModel)
        setListener(viewModel)
        setUpRecyclerView()
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
        with(args.project) {
            with(viewModel) {
                getAllSubTasks(projectId)
                getDoneTasksPercent(projectId)
            }
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

    private fun setUpProjectDetailsScreen() {
        with(binding) {
            with(args.project) {
                projectNameTextView.text = title
                projectTimeTextView.text = startDate!!.toEndDate(endDate!!)
                projectProgressButton.text = projectProgress?.value
                projectProgressButton.setTextColor(ContextCompat.getColor(requireContext(),
                    projectProgress?.color!!))
                with(projectEndInTimeTextView) {
                    timer(startDate, endDate)
                    countDownTimer.start()
                }
                setUpUpdateFieldsCustomViewFields(this)
            }
        }
    }

    private fun setUpUpdateFieldsCustomViewFields(projectDomain: ProjectDomain) {
        with(binding.updateFieldsCustomView) {
            with(projectDomain) {
                descriptionText = description
                titleText = title
                dateText = startDate?.toEndDate(endDate!!)
            }
        }
    }

    private fun setUpUpdateFieldsCustomView(viewModel: ProjectDetailsViewModel) {
        with(binding.updateFieldsCustomView) {
            chooseDateAction = { pickUpDate() }
            updateAction = { updateProject(viewModel) }
        }
    }

    private fun pickUpDate() {
        childFragmentManager.pickDate(startTime = args.project.startDate,
            endTime = args.project.endDate) { startingDate, endingDate ->
            binding.updateFieldsCustomView.dateText = startingDate.toEndDate(endingDate)
            startDate = startingDate
            endDate = endingDate
        }
    }

    private fun updateProject(viewModel: ProjectDetailsViewModel) {
        val project = ProjectDomain(
            title = binding.updateFieldsCustomView.getItemTitleText(),
            description = binding.updateFieldsCustomView.getItemDescriptionText(),
            endDate = endDate,
            startDate = startDate
        )
        viewModel.updateProject(args.project.projectId, project)
    }

    private fun observeUpdatedProjectLiveData(viewModel: ProjectDetailsViewModel) {
        observer(viewModel.updatedProjectLiveDomain) {
            project = it
            with(binding) {
                progressBarView.isVisible(false)
                projectNameTextView.text = it.title
                projectTimeTextView.text = it.startDate?.toEndDate(it.endDate!!)
                updateFieldsCustomView.dateText = it.startDate?.toEndDate(it.endDate!!)
                with(projectEndInTimeTextView) {
                    countDownTimer.cancel()
                    timer(it.startDate!!, it.endDate!!)
                    countDownTimer.start()
                }
                projectDetailsMotionLayout.transitionEndAction {
                    setUpUpdateFieldsCustomViewFields(it)
                }
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
            when (it) {
                is ActionTypes.Delete -> viewModel.deleteProject(args.project.projectId!!)
                is ActionTypes.Create -> createNewsTask()
            }
        }
    }

    private fun createNewsTask() {
        findNavController().navigate(ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToTaskCreatorFragment(
            project))
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

    private fun setUpRecyclerView() {
        with(binding.subTasksRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }

    override fun onItemClick(taskDomain: TaskDomain) {
        val bundle = bundle {
            putParcelable(TASK_DETAILS_ARGS_NAME, taskDomain)
            putParcelable(PROJECT_DETAILS_ARGS_NAME, project)
        }
        findNavController().safeNavigate(
            R.id.projectDetailsFragment,
            R.id.action_projectDetailsFragment_to_taskDetailsFragment,
            bundle)
    }

    companion object {
        private const val TASK_DETAILS_ARGS_NAME = "task"
        private const val PROJECT_DETAILS_ARGS_NAME = "project"
    }
}