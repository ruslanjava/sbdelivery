package ru.skillbranch.sbdelivery.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.extensions.attrColor

class BackgroundTiledView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    init {
        background = TilingDrawable(
            AppCompatResources.getDrawable(context, R.drawable.ic_splash_tile)!!,
            context.attrColor(R.attr.windowBackground)
        )
    }

}