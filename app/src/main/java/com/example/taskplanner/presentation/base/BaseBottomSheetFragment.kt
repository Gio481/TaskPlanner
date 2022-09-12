package com.example.taskplanner.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.taskplanner.util.ActionTypes
import com.example.taskplanner.util.BindingInflater
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<VB : ViewBinding> : BottomSheetDialogFragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    abstract val inflater: BindingInflater<VB>
    lateinit var action: (action: ActionTypes) -> Unit
    abstract fun setListener()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}