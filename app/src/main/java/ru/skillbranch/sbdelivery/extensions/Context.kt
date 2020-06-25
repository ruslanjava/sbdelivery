package ru.skillbranch.sbdelivery.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.core.content.res.ResourcesCompat


fun Context.attrColor(@AttrRes attrId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    val colorId = typedValue.resourceId
    return ResourcesCompat.getColor(resources, colorId, theme)
}

fun Context.isNetworkAvailable(): Boolean {
    try {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.isDefaultNetworkActive
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
    return false
}