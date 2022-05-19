package com.example.taskplanner.util.extensions

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.taskplanner.R

fun View.isVisible(visible: Boolean) {
    this.isVisible = visible
}

fun MotionLayout.transitionEndAction(action: () -> Unit) {
    this.setTransitionListener(
        object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
            ) {}

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float,
            ) {}

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (motionLayout?.currentState == R.id.start) {
                    action()
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float,
            ) {}
        }
    )
}

fun TextView.setTextColor(context: Context, color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun TextView.setBackgroundColor(context: Context, color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, color))
}