package ru.skillbranch.sbdelivery.http.data.auth

import com.squareup.moshi.Json

class RegisterRes {

    // Имя
    @Json(name = "firstName")
    lateinit var firstName: String

    // Фамилия
    @Json(name = "lastName")
    lateinit var lastName: String

    // E-mail пользователя
    @Json(name = "email")
    var email: String? = null

    // Пароль
    @Json(name = "password")
    var password: String? = null

}