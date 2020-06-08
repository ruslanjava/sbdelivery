package ru.skillbranch.sbdelivery.ui.screens.main

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.databinding.ViewMainMenuItemBinding

class MainMenuItemView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    init {
        val binding = ViewMainMenuItemBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

        val iconView = binding.mainMenuItemIcon
        val nameView = binding.mainMenuItemText
        val badgeView = binding.mainMenuItemBadge

        val ta = context.obtainStyledAttributes(attrs, R.styleable.MainMenuItemView)
        try {
            val imageId = ta.getResourceId(R.styleable.MainMenuItemView_icon, R.drawable.ic_sb)
            iconView.setImageResource(imageId)

            val name = ta.getString(R.styleable.MainMenuItemView_name)
            nameView.text = name

            if (ta.hasValue(R.styleable.MainMenuItemView_badge)) {
                val badge = ta.getInt(R.styleable.MainMenuItemView_badge, 0)
                badgeView.visibility = View.VISIBLE
                badgeView.text = "+$badge"
            } else {
                badgeView.visibility = View.INVISIBLE
            }
        } finally {
            ta.recycle()
        }
    }

}