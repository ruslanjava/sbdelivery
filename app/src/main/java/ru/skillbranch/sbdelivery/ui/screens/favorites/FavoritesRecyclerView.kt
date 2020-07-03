package ru.skillbranch.sbdelivery.ui.screens.favorites

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    init {
        layoutManager = GridLayoutManager(context, 2)
    }

}