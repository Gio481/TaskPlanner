package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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

        val projectSateText = typedArray.getString(R.styleable.StateCustomView_project_state)
        val totalProjectText = typedArray.getString(R.styleable.StateCustomView_total_project)
        val color = typedArray.getColor(R.styleable.StateCustomView_color,
            ContextCompat.getColor(context, R.color.blue_500))

        setProjectStateTextViewAttributes(projectSateText!!)
        setTotalProjectsTextViewAttributes(totalProjectText!!, color)
        typedArray.recycle()

    }

    private fun setProjectStateTextViewAttributes(newText: String) {
        with(binding.projectStateTextView) {
            text = newText
        }
    }

    private fun setTotalProjectsTextViewAttributes(newText: String, color: Int) {
        with(binding.totalProjectTextView) {
            text = newText
            background.setTint(color)
        }
    }
}