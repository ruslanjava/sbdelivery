package ru.skillbranch.sbdelivery.prefs

import android.content.SharedPreferences

class EncryptedPrefDelegate<X, T>(prefs: SharedPreferences, defaultValue: T) :
    PrefDelegate<X, T>(EncryptedSharedPreferences(prefs), defaultValue)