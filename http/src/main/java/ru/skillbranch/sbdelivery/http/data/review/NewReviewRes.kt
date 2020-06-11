package ru.skillbranch.sbdelivery.http.data.review

import com.squareup.moshi.Json

class NewReviewRes {

    // Оценка
    @Json(name = "rating")
    var rating: Float = 0f

    // Текст отзыва, опционально
    @Json(name = "text")
    var text: String? = null

}