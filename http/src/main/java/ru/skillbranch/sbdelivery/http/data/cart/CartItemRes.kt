package ru.skillbranch.sbdelivery.http.data.cart

import com.squareup.moshi.Json

// отдельный элемент списка блюд в корзине
class CartItemRes {

    // ID блюда
    @Json(name = "id")
    var dishId: String? = null

    // Количество
    @Json(name = "amount")
    var amount: Int = 0

    // Стоимость с учетом количества
    @Json(name = "number")
    var number: Int = 0

}