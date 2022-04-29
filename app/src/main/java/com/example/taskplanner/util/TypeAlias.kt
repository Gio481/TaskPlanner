package com.example.taskplanner.util

import android.view.LayoutInflater
import android.view.ViewGroup

typealias BindingInflater<VB> = (inflater: LayoutInflater, container: ViewGroup?, attachRoot: Boolean) -> VB
typealias ProgressListener = () -> Unit