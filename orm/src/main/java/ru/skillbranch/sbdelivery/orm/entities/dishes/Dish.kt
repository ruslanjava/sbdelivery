package ru.skillbranch.sbdelivery.orm.entities.dishes

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dish")
open class Dish {

    // ID блюда
    @PrimaryKey
    @NonNull
    lateinit var id: String

    // Название блюда
    var name: String? = null

    // Описание блюда
    var description: String? = null

    // Ссылка на изображение
    var image: String? = null

    // Старая цена, опционально
    var oldPrice: Int? = null

    // Текущая цена
    var price: Int = 0

    // Оценка пользователей
    var rating: Float = 0f

    // Количество комментариев
    var commentsCount: Int? = 0

    // Количество лайков
    var likes: Int = 0

    // ID категории, к которой относится блюдо
    @ColumnInfo(name = "category_id")
    var categoryId: String? = null

    // Доступно ли блюдо (нет - удалить из БД)
    var active: Boolean = false

    // Признак "избранного" блюда
    var favorite: Boolean = false

    // Дата создания (мс)
    var createdAt: Long = 0

    // Дата обновления (мс)
    var updatedAt: Long = 0

}