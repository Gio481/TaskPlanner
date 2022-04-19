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
        LayoutInflater.from(context), this, true)

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.InputTextCustomView,
            defStyleAttr,
            0)

        val hint = typedArray.getString(R.styleable.InputTextCustomView_hint)
        val helperText = typedArray.getString(R.styleable.InputTextCustomView_helperText)
        val helperTextColor = typedArray.getColor(R.styleable.InputTextCustomView_helperTextTextColor, Color.RED)
        setTextInputLayoutAttributes(hint!!, helperText!!, helperTextColor)

        val inputType = typedArray.getColor(R.styleable.InputTextCustomView_helperTextTextColor, Color.RED)
        val backgroundTint = typedArray.getColor(R.styleable.InputTextCustomView_android_background, Color.TRANSPARENT)
        setTextInputEditTextAttributes(inputType, backgroundTint)

        typedArray.recycle()

    }

    private fun setTextInputLayoutAttributes(
        editTextHint: String,
        textHelper: String,
        helperTextColor: Int,
    ) {
        with(binding.textInputLayout) {
            hint = editTextHint
            helperText = textHelper
            setHelperTextColor(ContextCompat.getColorStateList(context, helperTextColor))
        }
    }

    private fun setTextInputEditTextAttributes(textInputType: Int, backgroundTint: Int) {
        with(binding.textInputEditText) {
            inputType = textInputType
            background.setTint(backgroundTint)
        }
    }

    fun setPasswordToggleButton() {
        with(binding.textInputLayout) {
            endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(context,
                R.color.blue_700)))
        }
    }
}