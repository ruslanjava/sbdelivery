package ru.skillbranch.sbdelivery.http.auth

import okhttp3.Request

fun Request.identity(): Int {
    val shadow = method + url.toString()
    return shadow.hashCode()
}