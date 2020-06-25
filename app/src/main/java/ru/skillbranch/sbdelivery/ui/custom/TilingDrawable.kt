package ru.skillbranch.sbdelivery.ui.custom

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable

class TilingDrawable(drawable: Drawable, val backgroundColor: Int) : DrawableWrapper(drawable) {

    private var callbackEnabled = true

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = backgroundColor
        style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), paint)

        callbackEnabled = false
        val bounds = bounds
        val width = wrappedDrawable.intrinsicWidth
        val height = wrappedDrawable.intrinsicHeight
        var x = bounds.left
        while (x < bounds.right + width - 1) {
            var y = bounds.top
            while (y < bounds.bottom + height - 1) {
                wrappedDrawable.setBounds(x, y, x + width, y + height)
                wrappedDrawable.draw(canvas)
                y += height
            }
            x += width
        }
        callbackEnabled = true
    }

    override fun onBoundsChange(bounds: Rect) {
    }

    override fun invalidateDrawable(who: Drawable) {
        if (callbackEnabled) {
            super.invalidateDrawable(who)
        }
    }

    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        if (callbackEnabled) {
            super.scheduleDrawable(who, what, `when`)
        }
    }

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        if (callbackEnabled) {
            super.unscheduleDrawable(who, what)
        }
    }

}