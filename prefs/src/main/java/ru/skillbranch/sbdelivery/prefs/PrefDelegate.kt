package ru.skillbranch.sbdelivery.prefs

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal open class PrefDelegate<X, T>(var prefs: SharedPreferences, private val defaultValue: T) : ReadWriteProperty<X, T?> {

    private var actualValue: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: X, property: KProperty<*>): T? {
        if (actualValue == null) {
            with (prefs) {
                actualValue = when (defaultValue) {
                    is Boolean -> getBoolean(property.name, defaultValue as Boolean) as T
                    is String -> getString(property.name, defaultValue as String) as T
                    is Float -> getFloat(property.name, defaultValue as Float) as T
                    is Int -> getInt(property.name, defaultValue as Int) as T
                    is Long -> getLong(property.name, defaultValue as Long) as T
                    else -> throw IllegalArgumentException("Unsupported value: $defaultValue")
                }
            }
        }
        return actualValue
    }

    override fun setValue(thisRef: X, property: KProperty<*>, value: T?) {
        if (actualValue == value) {
            return
        }
        with (prefs.edit()) {
            when (value) {
                is Boolean -> putBoolean(property.name, value)
                is String -> putString(property.name, value)
                is Float -> putFloat(property.name, value)
                is Long -> putLong(property.name, value)
                is Int -> putInt(property.name, value)
                else -> throw IllegalArgumentException("Unsupported value: $defaultValue")
            }
            apply()
        }
        actualValue = value
    }

}