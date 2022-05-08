package com.example.taskplanner.util.extensions

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.example.taskplanner.databinding.CustomDialogBinding

private fun Dialog.setUpDialogLayout(binding: ViewBinding) {
    with(window!!) {
        setBackgroundDrawableResource(android.R.color.transparent)
        requestFeature(Window.FEATURE_NO_TITLE)
    }
    val params = window?.attributes
    with(params!!) {
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT
    }
    setContentView(binding.root)
}

fun Context.createDialog(
    chooseDateAction: () -> Unit,
    updateTaskAction: () -> Unit,
    deleteTaskAction: () -> Unit,
): Dialog {
    val customDialog = Dialog(this)
    val dialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(this))
    val dialog = customDialog.apply {
        setUpDialogLayout(dialogBinding)
        with(dialogBinding) {

            chooseDateButton.setOnClickListener {
                chooseDateAction()
            }

            updateTaskButton.setOnClickListener {
                updateTaskAction()
            }

            deleteTaskButton.setOnClickListener {
                deleteTaskAction()
                dismiss()
            }
        }
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
    return dialog
}