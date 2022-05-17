package com.example.taskplanner.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager(private val context: Context, private val activity: Activity) {

    fun permissionRequest(
        permission: List<String>,
        actionWhenPermissionIsGranted: () -> Unit,
        actionWhenPermissionIsDenied: () -> Unit,
        mediaPermissionChecker: () -> Unit,
    ) {
        permission.map {
            when {
                hasPermission(it) ->  actionWhenPermissionIsGranted()
                ActivityCompat.shouldShowRequestPermissionRationale(activity, it) ->  actionWhenPermissionIsDenied()
                else ->  mediaPermissionChecker()
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