package ru.skillbranch.sbdelivery.http.data.auth

import com.squareup.moshi.Json

class LoginRes {

    // E-mail пользователя
    @Json(name = "email")
    var email: String? = null

    // Пароль
    @Json(name = "password")
    var password: String? = null

}