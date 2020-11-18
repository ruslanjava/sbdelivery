package ru.skillbranch.sbdelivery.http.data.auth

data class RegisterReq(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)