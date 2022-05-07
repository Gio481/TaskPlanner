package com.example.taskplanner.util.extensions

import android.Manifest
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> Fragment.observer(liveData: LiveData<T>, observer: (data: T) -> Unit) {
    liveData.observe(this.viewLifecycleOwner) { observer(it) }
}

fun Fragment.showToast(message: String?) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.checkGalleryPermission(action: () -> Unit): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perm ->
        if (perm[Manifest.permission.READ_EXTERNAL_STORAGE] == true &&
            perm[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
        ) {
            action.invoke()
        }
    }
}