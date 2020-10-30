package ru.skillbranch.sbdelivery.http.data.auth

data class LoginReq(
    val email: String,
    val password: String
)