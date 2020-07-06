package ru.skillbranch.sbdelivery.orm.entities.cart

import androidx.room.Relation

class CartWithItems: Cart() {

    @Relation(
        entity = CartItem::class,
        parentColumn = "id",
        entityColumn = "cart_id"
    )
    var items: List<CartItemFull> = listOf()

}
