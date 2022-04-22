package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.taskplanner.R
import com.example.taskplanner.databinding.InputTextCustomViewBinding
import com.google.android.material.textfield.TextInputLayout

class InputTextCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: InputTextCustomViewBinding = InputTextCustomViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private var hint: String? = null
        set(value) {
            binding.textInputLayout.hint = value
            field = value
        }

    private var helperText: String? = null
        set(value) {
            binding.textInputLayout.helperText = value
            field = value
        }
    
    private var helperTextColor: Int = R.color.red
        set(value) {
            binding.textInputLayout.setHelperTextColor(
                ContextCompat.getColorStateList(context, helperTextColor)
            )
            field = value
        }

    private var inputType: Int = 0
        set(value) {
            binding.textInputEditText.inputType = value
            field = value
        }

    private var backgroundTint: Int = 0
        set(value) {
            binding.textInputEditText.background.setTint(value)
            field = value
        }

    private var background: Int = 0
        set(value) {
            binding.textInputEditText.setBackgroundResource(value)
            field = value
        }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.InputTextCustomView,
            defStyleAttr,
            0
        )

        hint = typedArray.getString(R.styleable.InputTextCustomView_hint)
        helperText = typedArray.getString(R.styleable.InputTextCustomView_helperText)
        helperTextColor =
            typedArray.getColor(R.styleable.InputTextCustomView_helperTextTextColor, 0)
        inputType =
            typedArray.getColor(R.styleable.InputTextCustomView_helperTextTextColor, Color.RED)
        backgroundTint = typedArray.getColor(R.styleable.InputTextCustomView_android_backgroundTint,
            Color.TRANSPARENT)
        background = typedArray.getResourceId(R.styleable.InputTextCustomView_android_background, 0)
        typedArray.recycle()
    }

    fun setPasswordToggleButton() {
        with(binding.textInputLayout) {
            endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            setEndIconTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.blue_700
                    )
                )
            )
        }
    }
}