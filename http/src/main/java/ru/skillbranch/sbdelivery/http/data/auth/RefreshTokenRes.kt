package ru.skillbranch.sbdelivery.http.data.auth

import com.squareup.moshi.Json

class RefreshTokenRes {

    // Токен восстановления
    @Json(name = "refreshToken")
    var refreshToken: String? = null

}