package ru.skillbranch.sbdelivery.repository.root

import ru.skillbranch.sbdelivery.http.data.dishes.DishRes
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

fun DishRes.toDish(): Dish {
    val dish = Dish()
    dish.id = id
    dish.name = name
    dish.description = description
    dish.image = image
    dish.oldPrice = oldPrice
    dish.price = price
    dish.rating = rating
    dish.commentsCount = commentsCount
    dish.likes = likes
    dish.categoryId = category
    dish.active = active
    dish.createdAt = createdAt
    dish.updatedAt = updatedAt
    return dish
}