package com.example.taskplanner.presentation.ui.home

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskplanner.R
import com.example.taskplanner.databinding.FragmentHomeBinding
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.home.adapter.OnClickListener
import com.example.taskplanner.presentation.ui.home.adapter.ProjectAdapter
import com.example.taskplanner.presentation.ui.home.viewmodel.HomeVewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.bundle
import com.example.taskplanner.util.extensions.isVisible
import com.example.taskplanner.util.extensions.observer
import com.example.taskplanner.util.extensions.safeNavigate
import com.example.taskplanner.util.extensions.showToast
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeVewModel>(), OnClickListener {
    override val inflater: BindingInflater<FragmentHomeBinding>
        get() = FragmentHomeBinding::inflate

    override fun getViewModelClass(): KClass<HomeVewModel> = HomeVewModel::class

    private val projectAdapter by lazy { ProjectAdapter(this) }

    override fun onBindViewModel(viewModel: HomeVewModel) {
        binding.progressBarView.isVisible(true)
        with(viewModel) {
            getUserInfo()
            getAllProjects()
            getAllProjectsSize()
        }
        setUpRecyclerView()
        observeUserLiveData(viewModel)
        observeErrorLiveData(viewModel)
        observeAllProjectsLiveData(viewModel)
        observeProjectProgressLiveData(viewModel)
        observeSuccessLogOutLiveData(viewModel)
        setListener(viewModel)
    }

    private fun observeUserLiveData(viewModel: HomeVewModel) {
        observer(viewModel.userLiveData) {
            binding.progressBarView.isVisible(false)
            setUpUser(it.name, it.job)
        }
    }

    private fun setUpUser(name: String?, job: String?) {
        with(binding) {
            userNameEditText.setText(name)
            userJobEditText.setText(job)
        }
    }

    private fun observeSuccessLogOutLiveData(viewModel: HomeVewModel) {
        observer(viewModel.successLiveData) {
            findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
        }
    }

    private fun observeAllProjectsLiveData(viewModel: HomeVewModel) {
        observer(viewModel.allProjectsLiveData) {
            binding.progressBarView.isVisible(false)
            projectAdapter.submitList(it)
        }
    }

    private fun observeProjectProgressLiveData(viewModel: HomeVewModel) {
        with(viewModel) {
            with(binding) {
                observer(getAllTodoProjects) {
                    todoProjectsCustomView.totalProjectText = it.toString()
                }
                observer(getAllInProgressProjects) {
                    inProgressProjectsCustomView.totalProjectText = it.toString()
                }
                observer(getAllDoneProjects) {
                    doneProjectsCustomView.totalProjectText = it.toString()
                }
            }
        }
    }

    private fun observeErrorLiveData(viewModel: HomeVewModel) {
        observer(viewModel.errorLiveData) {
            showToast(it)
        }
    }

    private fun setListener(viewModel: HomeVewModel) {
        with(binding) {
            addItemActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_projectCreatorFragment)
            }
            logOutView.setOnClickListener {
                viewModel.logOut()
            }
        }
    }

    private fun setUpRecyclerView() {
        with(binding.projectsRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = projectAdapter
        }
    }

    override fun onItemClickListener(projectDomain: ProjectDomain) {
        val bundle = bundle { putParcelable(PROJECT_DETAILS_ARGS_NAME, projectDomain) }
        findNavController().safeNavigate(
            R.id.homeFragment,
            R.id.action_homeFragment_to_projectDetailsFragment,
            bundle)
    }

    companion object {
        private const val PROJECT_DETAILS_ARGS_NAME = "project"
    }
}