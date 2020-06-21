package ru.skillbranch.sbdelivery.orm.entities.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
class CartItem {

    // ID блюда
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long? = null

    // ID корзины
    @ColumnInfo(name = "cart_id")
    var cartId: Long? = null

    // ID блюда
    @ColumnInfo(name = "dish_id")
    var dishId: String? = null

    // Количество
    @ColumnInfo(name = "amount")
    var amount: Int = 0

    // Стоимость с учетом количества
    @ColumnInfo(name = "number")
    var number: Int = 0

}