package com.example.taskplanner.presentation.ui.task.details.actions

import com.example.taskplanner.databinding.FragmentTaskBottomSheetBinding
import com.example.taskplanner.presentation.base.BaseBottomSheetFragment
import com.example.taskplanner.util.ActionTypes
import com.example.taskplanner.util.BindingInflater

class TaskBottomSheetFragment : BaseBottomSheetFragment<FragmentTaskBottomSheetBinding>() {

    override val inflater: BindingInflater<FragmentTaskBottomSheetBinding>
        get() = FragmentTaskBottomSheetBinding::inflate

    override fun setListener() {
        with(binding) {
            updateItemCustomView.setOnClickListener {
                action(ActionTypes.Update)
                dismiss()
            }
            updateAllItemsCustomView.setOnClickListener {
                action(ActionTypes.UpdateAll)
                dismiss()
            }
            deleteTaskCustomView.setOnClickListener {
                action(ActionTypes.Delete)
                dismiss()
            }
        }
    }
}