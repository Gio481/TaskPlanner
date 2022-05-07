package com.example.taskplanner.presentation.ui.project.create

import androidx.navigation.fragment.findNavController
import com.example.taskplanner.databinding.FragmentProjectCreatorBinding
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.project.create.viewmodel.ProjectCreatorViewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.Progress
import com.example.taskplanner.util.extensions.*
import kotlin.reflect.KClass

class ProjectCreatorFragment :
    BaseFragment<FragmentProjectCreatorBinding, ProjectCreatorViewModel>() {

    override val inflater: BindingInflater<FragmentProjectCreatorBinding>
        get() = FragmentProjectCreatorBinding::inflate

    override fun getViewModelClass(): KClass<ProjectCreatorViewModel> =
        ProjectCreatorViewModel::class

    private var startDate: Long? = null
    private var endDate: Long? = null
    private var project: ProjectDomain = ProjectDomain()

    override fun onBindViewModel(viewModel: ProjectCreatorViewModel) {
        observeErrorLiveData(viewModel)
        observeSuccessLiveData(viewModel)
        setListener(viewModel)
    }

    private fun observeSuccessLiveData(viewModel: ProjectCreatorViewModel) {
        observer(viewModel.projectDomainLiveData) {
            binding.progressBarView.isVisible(false)
            findNavController().navigate(ProjectCreatorFragmentDirections.actionProjectCreatorFragmentToProjectDetailsFragment(
                it))
        }
    }

    private fun observeErrorLiveData(viewModel: ProjectCreatorViewModel) {
        observer(viewModel.errorLiveData) {
            binding.progressBarView.isVisible(false)
            showToast(it)
        }
    }

    private fun setListener(viewModel: ProjectCreatorViewModel) {
        with(binding) {
            datePickerButton.setOnClickListener {
                pickUpDate()
            }
            createNewProjectButton.setOnClickListener {
                binding.progressBarView.isVisible(true)
                createNewProject(viewModel)
            }
        }
    }

    private fun pickUpDate() {
        childFragmentManager.pickDate { startingDate, endingDate ->
            binding.projectTimeTextView.text = startingDate.toEndDate(endingDate)
            startDate = startingDate
            endDate = endingDate
        }
    }

    private fun createNewProject(viewModel: ProjectCreatorViewModel) {
        project = ProjectDomain(
            title = binding.projectNameEditText.text.toString(),
            description = binding.projectDescriptionEditText.text.toString(),
            startDate = startDate,
            endDate = endDate,
            projectProgress = Progress.TODO
        )
        viewModel.createProject(project)
    }
}