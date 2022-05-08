package com.example.taskplanner.presentation.ui.project.details.actions

import com.example.taskplanner.databinding.FragmentProjectBottomSheetBinding
import com.example.taskplanner.presentation.base.BaseBottomSheetFragment
import com.example.taskplanner.util.ActionTypes
import com.example.taskplanner.util.BindingInflater

class ProjectActionsBottomSheetFragment :
    BaseBottomSheetFragment<FragmentProjectBottomSheetBinding>() {

    override val inflater: BindingInflater<FragmentProjectBottomSheetBinding>
        get() = FragmentProjectBottomSheetBinding::inflate

    override fun setListener() {
        with(binding) {
            binding.createNewsTaskCustomView.setOnClickListener {
                action(ActionTypes.Create)
                dismiss()
            }
            deleteProjectCustomView.setOnClickListener {
                action(ActionTypes.Delete)
                dismiss()
            }
        }
    }
}