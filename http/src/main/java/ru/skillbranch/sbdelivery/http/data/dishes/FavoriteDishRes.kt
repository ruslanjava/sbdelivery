package ru.skillbranch.sbdelivery.http.data.dishes

import com.squareup.moshi.Json

class FavoriteDishRes {

    @Json(name = "dishId")
    lateinit var dishId: String

    @Json(name = "favorite")
    var favorite: Boolean = false

    @Json(name = "gender")
    var updatedAt: Long? = null

}