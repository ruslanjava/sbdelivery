package ru.skillbranch.sbdelivery.http.data.profile

import com.squareup.moshi.Json

class ProfileRes {

    // ID пользователя
    @Json(name = "id")
    lateinit var id: String

    // Имя
    @Json(name = "firstName")
    lateinit var firstName: String

    // Фамилия
    @Json(name = "lastName")
    lateinit var lastName: String

    // Email
    @Json(name = "email")
    lateinit var email: String

}