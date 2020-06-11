package ru.skillbranch.sbdelivery.http.data.recovery

import com.squareup.moshi.Json

class RecoveryEmailRes {

    // E-mail пользователя
    @Json(name = "email")
    var email: String? = null

}