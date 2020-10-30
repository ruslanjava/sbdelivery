package ru.skillbranch.sbdelivery.http.data.recovery

import com.squareup.moshi.Json

class RecoveryPasswordReq {

    // E-mail пользователя
    @Json(name = "email")
    var email: String? = null

    // Код восстановления
    @Json(name = "code")
    var code: String? = null

    // Новый пароль
    @Json(name = "password")
    var password: String? = null

}