package com.example.taskplanner.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager(private val context: Context, private val activity: Activity) {

    fun mediaPermissionRequest(
        actionWhenPermissionIsGranted: () -> Unit,
        actionWhenPermissionIsDenied: () -> Unit,
        mediaPermissionChecker: () -> Unit,
    ) {
        when {
            hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                actionWhenPermissionIsGranted()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                actionWhenPermissionIsDenied()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> actionWhenPermissionIsDenied()
            else -> {
                mediaPermissionChecker()
            }
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}