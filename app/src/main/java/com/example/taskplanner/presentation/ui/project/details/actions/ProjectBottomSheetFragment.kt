package com.example.taskplanner.presentation.ui.project.details.actions

import com.example.taskplanner.databinding.FragmentProjectBottomSheetBinding
import com.example.taskplanner.presentation.base.BaseBottomSheetFragment
import com.example.taskplanner.util.ActionTypes
import com.example.taskplanner.util.BindingInflater

class ProjectBottomSheetFragment : BaseBottomSheetFragment<FragmentProjectBottomSheetBinding>() {

    override val inflater: BindingInflater<FragmentProjectBottomSheetBinding>
        get() = FragmentProjectBottomSheetBinding::inflate

    override fun setListener() {
        with(binding) {
            itemDescriptionCustomView.setOnClickListener {
                action(ActionTypes.Description)
                dismiss()
            }
            deleteProjectCustomView.setOnClickListener {
                action(ActionTypes.Delete)
                dismiss()
            }
            updateItemCustomView.setOnClickListener {
                action(ActionTypes.Update)
                dismiss()
            }
            updateAllItemsCustomView.setOnClickListener {
                action(ActionTypes.UpdateAll)
                dismiss()
            }
        }
    }
}