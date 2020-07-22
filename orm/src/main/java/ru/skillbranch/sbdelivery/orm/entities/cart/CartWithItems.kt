package ru.skillbranch.sbdelivery.orm.entities.cart

import androidx.room.Ignore

class CartWithItems: Cart() {

    @Ignore
    var items: List<CartItemFull> = listOf()

}
