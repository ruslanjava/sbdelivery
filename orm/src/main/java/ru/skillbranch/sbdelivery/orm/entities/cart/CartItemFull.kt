package ru.skillbranch.sbdelivery.orm.entities.cart

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

@DatabaseView("""
    SELECT amount AS amount, 
    id AS dish_id, dish.name AS dish_name, dish.description AS dish_description, 
    dish.image as dish_image, dish.price as dish_price
    FROM cart_item 
    INNER JOIN dish AS dish ON id = dish.id
""")
class CartItemFull {

    // ID блюда
    @Embedded(prefix = "dish_")
    lateinit var dish: Dish

    // Количество
    @ColumnInfo(name = "amount")
    var amount: Int = 0

    // Стоимость с учетом количества
    val fullPrice: Int by lazy {
        dish.price * amount
    }

}