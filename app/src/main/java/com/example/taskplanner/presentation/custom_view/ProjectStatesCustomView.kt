package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskplanner.R
import com.example.taskplanner.databinding.ProjectStatesCustomViewBinding

class ProjectStatesCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ProjectStatesCustomViewBinding = ProjectStatesCustomViewBinding.inflate(
        LayoutInflater.from(context), this, true)

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.StateCustomView,
            defStyleAttr,
            0)
        setProjectStateTextViewAttributes(typedArray)
        setTotalProjectsTextViewAttributes(typedArray)
    }

    private fun setProjectStateTextViewAttributes(typedArray: TypedArray) {
        with(binding.projectStateTextView) {
            text = typedArray.getString(R.styleable.StateCustomView_project_state)
        }
        typedArray.recycle()
    }

    private fun setTotalProjectsTextViewAttributes(typedArray: TypedArray) {
        with(binding.totalProjectTextView) {
            text = typedArray.getString(R.styleable.StateCustomView_total_project)
            val color = typedArray.getColor(R.styleable.StateCustomView_color,
                Color.parseColor(TODO_STATE_COLOR))
            background.setTint(color)
        }
        typedArray.recycle()
    }

    companion object {
        private const val TODO_STATE_COLOR = "#4CABCE"
    }

}