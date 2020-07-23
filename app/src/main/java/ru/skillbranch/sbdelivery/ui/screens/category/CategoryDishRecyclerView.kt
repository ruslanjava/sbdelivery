package ru.skillbranch.sbdelivery.ui.screens.category

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryDishRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    init {
        layoutManager = GridLayoutManager(context, 2)
    }

}