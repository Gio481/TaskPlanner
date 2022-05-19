package com.example.taskplanner.util.extensions

import android.Manifest
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import android.app.AlertDialog
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import com.example.taskplanner.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch

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

fun FragmentManager.pickDate(
    startTime: Long? = null,
    endTime: Long? = null,
    validator: CalendarConstraints.DateValidator = DateValidatorPointForward.now(),
    action: (startDate: Long, endDate: Long) -> Unit,
) {
    val constraints = CalendarConstraints.Builder()
        .setValidator(validator).build()

    val picker = with(MaterialDatePicker.Builder.dateRangePicker()) {
        setTitleText(R.string.date_picker_dialog_title)
        setCalendarConstraints(constraints)
        if (startTime != null && endTime != null) {
            setSelection(Pair(startTime, endTime))
        }
        build()
    }

    picker.addOnPositiveButtonClickListener {
        val startDate = it.first
        val endDate = it.second
        action(startDate, endDate)
    }
    picker.addOnNegativeButtonClickListener {
        picker.dismiss()
    }
    picker.show(this, null)
}

fun Fragment.inflatePopupMenu(
    view: View,
    todoAction: (() -> Unit)? = null,
    inProgressAction: (() -> Unit)? = null,
    doneAction: (() -> Unit)? = null,
) {
    val popupMenu = PopupMenu(requireContext(), view)
    val inflater = popupMenu.menuInflater
    inflater.inflate(R.menu.popup_menu, popupMenu.menu)
    popupMenu.show()

    popupMenu.setOnMenuItemClickListener {
        when (it.itemId) {
            R.id.todoItem -> {
                todoAction?.invoke()
            }
            R.id.inProgressItem -> {
                inProgressAction?.invoke()
            }
            R.id.doneItem -> {
                doneAction?.invoke()
            }
        }
        true
    }
}

fun Fragment.launchScope(launch: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        launch()
    }
}

fun Fragment.alertDialog(block: AlertDialog.Builder.() -> Unit): AlertDialog? {
    return AlertDialog.Builder(requireContext()).apply { block() }.show()
}