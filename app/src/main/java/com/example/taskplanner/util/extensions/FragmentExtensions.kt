package com.example.taskplanner.util.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> Fragment.observer(liveData: LiveData<T>, observer: (data: T) -> Unit) {
    liveData.observe(this.viewLifecycleOwner) { observer(it) }
}

fun Fragment.showToast(message: String?) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}