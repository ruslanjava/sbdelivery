package ru.skillbranch.sbdelivery.orm.entities.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class Cart {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 1

    // Промокод, опционально
    @ColumnInfo(name = "promocode")
    var promocode: String? = null

    // Текст промокода, опционально
    @ColumnInfo(name = "promotext")
    var promotext: String? = null

    // Общая стоимость корзины
    @ColumnInfo(name = "total")
    var total: Int = 0

    @Ignore
    var items: List<CartItem> = listOf()

}