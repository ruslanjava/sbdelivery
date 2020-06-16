package ru.skillbranch.sbdelivery.prefs

import android.content.Context
import android.content.SharedPreferences

class PrefLogin(context: Context) {

    private val prefs : SharedPreferences = context.getSharedPreferences(
        "login", Context.MODE_PRIVATE
    )

    // E-mail пользователя
    var email by PrefDelegate(prefs, "")

    // Пароль
    var password by EncryptedPrefDelegate(prefs, "")

    fun clear() {
        prefs.edit().clear().apply()
    }

}