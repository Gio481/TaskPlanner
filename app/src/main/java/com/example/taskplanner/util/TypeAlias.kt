package com.example.taskplanner.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskplanner.domain.model.TaskDomain

typealias BindingInflater<VB> = (inflater: LayoutInflater, container: ViewGroup?, attachRoot: Boolean) -> VB
typealias ProgressListener = (view: View, taskId: String) -> Unit
typealias OnItemClickListener = (taskDomain: TaskDomain) -> Unit