package ru.skillbranch.sbdelivery.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.NumberPicker
import androidx.appcompat.widget.AppCompatTextView
import ru.skillbranch.sbdelivery.databinding.ViewNumberPickerBinding

class NumberPickerView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    private val decreaseButton: AppCompatTextView
    private val valueView: AppCompatTextView
    private val increaseButton: AppCompatTextView

    private var number: Int = 1
    private var listener: NumberPicker.OnValueChangeListener? = null

    init {
        val binding = ViewNumberPickerBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

        decreaseButton = binding.numberPickerDecrease
        valueView = binding.numberPickerNumber
        increaseButton = binding.numberPickerIncrease

        decreaseButton.setOnClickListener { setValue(number - 1) }
        increaseButton.setOnClickListener {setValue(number + 1) }
    }

    fun setValue(value: Int) {
        this.number = value
        valueView.text = number.toString()
        decreaseButton.isEnabled = value > 0
    }

    fun setOnValueChangedListener(listener: NumberPicker.OnValueChangeListener) {
        this.listener = listener
    }

}