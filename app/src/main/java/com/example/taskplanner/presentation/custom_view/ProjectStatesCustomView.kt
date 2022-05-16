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

    private var projectStateText: String? = null
        set(value) {
            binding.projectStateTextView.text = value
            field = value
        }

    var totalProjectText: String? = null
        set(value) {
            binding.totalProjectTextView.text = value
            field = value
        }

    private var background: Int = 0
        set(value) {
            binding.totalProjectTextView.background.setTint(value)
            field = value
        }

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.StateCustomView,
            defStyleAttr,
            0)

        projectStateText = typedArray.getString(R.styleable.StateCustomView_project_state)
        totalProjectText = typedArray.getString(R.styleable.StateCustomView_total_project)
        background = typedArray.getColor(R.styleable.StateCustomView_color,
            ContextCompat.getColor(context, R.color.blue_500))
        typedArray.recycle()

    }
}