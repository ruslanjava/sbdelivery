package ru.skillbranch.sbdelivery.orm.entities.dishes

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class Category {

    // ID категории
    @PrimaryKey
    @NonNull
    lateinit var id: String

    // Название категории
    var name: String? = null

    // Номер по порядку
    var order: Int = 0

    // Ссылка на иконку, опционально
    var image: String? = null

    // ID Родительской категории, опционально (если есть, то это подкатегория)
    @ColumnInfo(name = "parent_id")
    var parentId: String? = null

    // Доступна ли категория (если нет, то нужно удалить ее из БД)
    var active: Boolean = false

    // Дата создания (в миллисекундах)
    var createdAt: Long = 0

    // Дата обновления (в миллисекундах)
    var updatedAt: Long = 0

}