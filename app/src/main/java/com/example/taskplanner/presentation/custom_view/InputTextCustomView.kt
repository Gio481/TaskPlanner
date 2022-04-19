package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskplanner.R
import com.example.taskplanner.databinding.AuthenticationCustomViewBinding
import com.google.android.material.textfield.TextInputLayout

class InputTextCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: AuthenticationCustomViewBinding = AuthenticationCustomViewBinding.inflate(
        LayoutInflater.from(context), this, true)

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.InputTextCustomView,
            defStyleAttr,
            0)
        setTextInputLayoutAttributes(typedArray)
        setTextInputEditTextAttributes(typedArray)
    }

    private fun setTextInputLayoutAttributes(typedArray: TypedArray) {
        with(binding.textInputLayout) {
            hint = typedArray.getString(R.styleable.InputTextCustomView_hint)
            helperText = typedArray.getString(R.styleable.InputTextCustomView_helperText)
            val color =
                typedArray.getColor(R.styleable.InputTextCustomView_helperTextTextColor, Color.RED)
            setHelperTextColor(ColorStateList.valueOf(color))
        }

        typedArray.recycle()
    }

    private fun setTextInputEditTextAttributes(typedArray: TypedArray) {
        with(binding.textInputEditText) {
            inputType = typedArray.getInt(
                R.styleable.InputTextCustomView_android_inputType,
                InputType.TYPE_CLASS_TEXT)
            val color =
                typedArray.getColor(R.styleable.InputTextCustomView_android_background,
                    Color.TRANSPARENT)
            background.setTint(color)
        }
        typedArray.recycle()
    }

    fun setPasswordToggleButton() {
        with(binding.textInputLayout) {
            endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            setEndIconTintList(ColorStateList.valueOf(Color.parseColor(PASSWORD_TOGGLE_BUTTON_COLOR)))
        }
    }

    companion object {
        private const val PASSWORD_TOGGLE_BUTTON_COLOR = "#004481"
    }
}