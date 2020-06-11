package ru.skillbranch.sbdelivery.http.data.auth

import com.squareup.moshi.Json

class LoggedRes {

    // ID пользователя
    @Json(name = "id")
    var id: String? = null

    // Имя
    @Json(name = "firstName")
    lateinit var firstName: String

    // Фамилия
    @Json(name = "lastName")
    lateinit var lastName: String

    // E-mail пользователя
    @Json(name = "email")
    var email: String? = null

    // Токен доступа
    @Json(name = "accessToken")
    var accessToken: String? = null

    // Токен восстановления
    @Json(name = "refreshToken")
    var refreshToken: String? = null

}