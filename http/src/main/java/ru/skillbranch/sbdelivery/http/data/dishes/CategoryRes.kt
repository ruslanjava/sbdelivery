package ru.skillbranch.sbdelivery.http.data.dishes

import com.squareup.moshi.Json

class CategoryRes {

    // ID категории
    @Json(name = "id")
    lateinit var id: String

    // Название категории
    @Json(name = "name")
    lateinit var name: String

    // Номер по порядку
    @Json(name = "order")
    var order: Int = 0

    // Ссылка на иконку, опционально
    @Json(name = "image")
    var image: String? = null

    // ID Родительской категории, опционально (если есть, то это подкатегория)
    @Json(name = "parent")
    var parent: String? = null

    // Доступна ли категория (если нет, то нужно удалить ее из БД)
    @Json(name = "active")
    var active: Boolean = false

    // Дата создания (в миллисекундах)
    @Json(name = "createdAt")
    var createdAt: Long = 0

    // Дата обновления (в миллисекундах)
    @Json(name = "updatedAt")
    var updatedAt: Long = 0

}