package ru.skillbranch.sbdelivery.data.remote

import com.squareup.moshi.Json

class DishRes {

    @Json(name = "id")
    lateinit var id: String

    @Json(name = "name")
    lateinit var name: String

    @Json(name = "description")
    lateinit var description: String

    @Json(name = "image")
    lateinit var image: String

    @Json(name = "oldPrice")
    var oldPrice: Int? = null

    @Json(name = "price")
    var price: Int = 0

    @Json(name = "rating")
    var rating: Float? = null

    @Json(name = "commentsCount")
    var commentsCount: Int? = 0

    @Json(name = "likes")
    var likes: Int = 0

    @Json(name = "category")
    lateinit var category: String

    @Json(name = "active")
    var active: Boolean = false

    @Json(name = "createdAt")
    var createdAt: Long = 0

    @Json(name = "updatedAt")
    var updatedAt: Long = 0

}