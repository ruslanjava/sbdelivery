package ru.skillbranch.sbdelivery.orm.entities.cart

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
class CartItem {

    // ID блюда
    @PrimaryKey
    @NonNull
    lateinit var id: String

    // Количество
    @ColumnInfo(name = "amount")
    var amount: Int = 0

}
