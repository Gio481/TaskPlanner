package com.example.taskplanner.util.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController

fun NavController.safeNavigate(
    @IdRes currentDestinationId: Int,
    @IdRes destinationId: Int,
    args: Bundle? = null,
) {
    if (currentDestinationId == currentDestination?.id) {
        navigate(destinationId, args)
    }
}