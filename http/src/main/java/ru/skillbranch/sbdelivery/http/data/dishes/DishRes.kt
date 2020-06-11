package ru.skillbranch.sbdelivery.http.data.dishes

import com.squareup.moshi.Json

class DishRes {

    // ID блюда
    @Json(name = "id")
    lateinit var id: String

    // Название блюда
    @Json(name = "name")
    lateinit var name: String

    // Описание блюда
    @Json(name = "description")
    lateinit var description: String

    // Ссылка на изображение
    @Json(name = "image")
    lateinit var image: String

    // Старая цена, опционально
    @Json(name = "oldPrice")
    var oldPrice: Int? = null

    // Текущая цена
    @Json(name = "price")
    var price: Int = 0

    // Оценка пользователей
    @Json(name = "rating")
    var rating: Float = 0f

    // Количество комментариев
    @Json(name = "commentsCount")
    var commentsCount: Int? = 0

    // Количество лайков
    @Json(name = "likes")
    var likes: Int = 0

    // ID категории, к которой относится блюдо
    @Json(name = "category")
    lateinit var category: String

    // Доступно ли блюдо (нет - удалить из БД)
    @Json(name = "active")
    var active: Boolean = false

    // Дата создания (мс)
    @Json(name = "createdAt")
    var createdAt: Long = 0

    // Дата обновления (мс)
    @Json(name = "updatedAt")
    var updatedAt: Long = 0

}