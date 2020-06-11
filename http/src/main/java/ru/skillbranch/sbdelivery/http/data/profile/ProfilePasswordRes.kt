package ru.skillbranch.sbdelivery.http.data.profile

import com.squareup.moshi.Json

class ProfilePasswordRes {

    // Имя
    @Json(name = "oldPassword")
    lateinit var oldPassword: String

    // Имя
    @Json(name = "oldPassword")
    lateinit var newPassword: String

}