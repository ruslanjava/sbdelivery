package ru.skillbranch.sbdelivery.http

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonConverter {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

}
