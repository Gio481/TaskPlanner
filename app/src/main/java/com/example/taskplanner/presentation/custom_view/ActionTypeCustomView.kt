package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskplanner.R
import com.example.taskplanner.databinding.ActionTypeCustomViewBinding

class ActionTypeCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ActionTypeCustomViewBinding = ActionTypeCustomViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var actionBackground: Int? = null
        set(value) {
            value?.let { binding.actionTypeView.setBackgroundResource(it) }
            field = value
        }

    var actionName: String? = null
        set(value) {
            binding.actionTypeTextView.text = value
            field = value
        }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ActionTypeCustomView,
            defStyleAttr,
            0
        )

        actionBackground =
            typedArray.getResourceId(R.styleable.ActionTypeCustomView_background, 0)
        actionName = typedArray.getString(R.styleable.ActionTypeCustomView_actionName)
        typedArray.recycle()
    }

}