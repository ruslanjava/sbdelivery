package ru.skillbranch.sbdelivery.http.data.review

import com.squareup.moshi.Json

class ReviewRes {

    // ID отзыва
    @Json(name = "id")
    lateinit var id: String

    // Имя автора
    @Json(name = "author")
    lateinit var author: String

    // Дата написания отзыва (ISO 8601)
    @Json(name = "date")
    lateinit var date: String

    // Оценка
    @Json(name = "rating")
    var rating: Float = 0f

    // Текст отзыва, опционально
    @Json(name = "text")
    var text: String? = null

    // Доступен ли отзыв (нет - удалить из БД)
    @Json(name = "active")
    var active: Boolean = false

    // Дата создания (мс)
    @Json(name = "active")
    var createdAt: Long = 0

    // Дата обновления (мс)
    @Json(name = "active")
    var updatedAt: Long = 0

}