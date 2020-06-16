package ru.skillbranch.sbdelivery.prefs

import android.content.Context
import android.content.SharedPreferences

class PrefProfile(context: Context) {

    private val prefs : SharedPreferences = context.getSharedPreferences(
        "profile", Context.MODE_PRIVATE
    )

    // ID пользователя
    var id by PrefDelegate(prefs, "")

    // Имя
    var firstName by PrefDelegate(prefs, "")

    // Фамилия
    var lastName by PrefDelegate(prefs, "")

    // Email
    var email by PrefDelegate(prefs, "")

    fun clear() {
        prefs.edit().clear().apply()
    }

}