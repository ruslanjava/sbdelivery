package ru.skillbranch.sbdelivery.ui.subCategoryList

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SubCategoryRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    init {
        layoutManager = GridLayoutManager(context, 2)
    }

}