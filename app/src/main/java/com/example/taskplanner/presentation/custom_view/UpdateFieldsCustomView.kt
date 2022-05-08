package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.taskplanner.R
import com.example.taskplanner.databinding.UpdateFieldsCustomViewBinding

class UpdateFieldsCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: UpdateFieldsCustomViewBinding = UpdateFieldsCustomViewBinding.inflate(
        LayoutInflater.from(context), this, true)

    lateinit var chooseDateAction: () -> Unit
    lateinit var updateAction: () -> Unit

    var descriptionText: String? = null
        set(value) {
            binding.itemDescriptionEditText.setText(value)
            field = value
        }

    var titleText: String? = null
        set(value) {
            binding.itemTitleEditText.setText(value)
            field = value
        }

    var dateText: String? = null
        set(value) {
            binding.itemDateTextView.text = value
            field = value
        }

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.UpdateFieldsCustomView,
            defStyleAttr,
            0)
        descriptionText = typedArray.getString(R.styleable.UpdateFieldsCustomView_descriptionText)
        titleText = typedArray.getString(R.styleable.UpdateFieldsCustomView_titleText)
        dateText = typedArray.getString(R.styleable.UpdateFieldsCustomView_dateText)
        typedArray.recycle()
        setListener()
    }

    private fun setListener() {
        with(binding) {
            chooseDateButton.setOnClickListener {
                chooseDateAction()
            }
            updateItemActionButton.setOnClickListener {
                updateAction()
            }
        }
    }

    fun getItemDescriptionText(): String {
        return binding.itemDescriptionEditText.text.toString()
    }

    fun getItemTitleText(): String {
        return binding.itemTitleEditText.text.toString()
    }

}