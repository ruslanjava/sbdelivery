package ru.skillbranch.sbdelivery.orm.entities.cart

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

@DatabaseView("""
    SELECT 
    cart_item.id AS dish_id, dish.name AS dish_name, dish.description AS dish_description, 
    dish.image AS dish_image, dish.price AS dish_price,
    dish.likes AS dish_likes, dish.active AS dish_active,
    dish.favorite AS dish_favorite, dish.recommended AS dish_recommended,
    dish.rating AS dish_rating, dish.createdAt as dish_createdAt,
    dish.updatedAt as dish_updatedAt,
    cart_item.amount AS amount
    FROM cart_item AS cart_item
    LEFT JOIN dish ON cart_item.id = dish.id
""")
data class CartItemFull(

    // ID блюда
    @Embedded(prefix = "dish_")
    val dish: Dish,

    // Количество
    @ColumnInfo(name = "amount")
    val amount: Int = 0

) {

    // Стоимость с учетом количества
    fun getFullPrice(): Int {
        return dish.price * amount
    }

}