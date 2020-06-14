package ru.skillbranch.sbdelivery.http.data.cart

import com.squareup.moshi.Json
import ru.skillbranch.sbdelivery.http.data.cart.CartItemRes

class CartRes {

    // Промокод, опционально
    @Json(name = "promocode")
    var promocode: String? = null

    // Текст промокода, опционально
    @Json(name = "promocode")
    var promotext: String? = null

    // Общая стоимость корзины
    @Json(name = "total")
    var total: Int = 0

    // Список блюд
    @Json(name = "items")
    var items: List<CartItemRes> = listOf()

}