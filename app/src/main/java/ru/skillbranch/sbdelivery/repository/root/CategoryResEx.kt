package ru.skillbranch.sbdelivery.repository.root

import ru.skillbranch.sbdelivery.http.data.dishes.CategoryRes
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category

fun CategoryRes.toCategory(): Category {
    val category = Category()
    category.id = this.id
    category.name = this.name
    category.order = this.order
    category.image = this.image
    category.parentId = this.parent ?: "root"
    category.active = this.active
    category.updatedAt = this.updatedAt
    return category
}