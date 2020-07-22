package ru.skillbranch.sbdelivery.orm.entities.cart

import androidx.annotation.NonNull
import androidx.room.*
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

@Entity(
    tableName = "cart_item",
    foreignKeys = [
        ForeignKey(entity = Cart::class, parentColumns = ["id"], childColumns = ["cart_id"]
    )],
    indices = [
        Index("cart_id")
    ]
)
class CartItem {

    // ID блюда
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    lateinit var id: String

    // ID корзины
    @NonNull
    @ColumnInfo(name = "cart_id")
    var cartId: Long = 1

    // Количество
    @ColumnInfo(name = "amount")
    var amount: Int = 0

}