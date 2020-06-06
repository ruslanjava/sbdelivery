package ru.skillbranch.sbdelivery.data.remote

import com.squareup.moshi.Json

class FavoriteDish {

    @Json(name = "dishId")
    lateinit var dishId: String

    @Json(name = "favorite")
    var favorite: Boolean = false

    @Json(name = "gender")
    var updatedAt: Long? = null

}