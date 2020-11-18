package ru.skillbranch.sbdelivery.repository.root

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.skillbranch.sbdelivery.application.SbDeliveryApplication
import ru.skillbranch.sbdelivery.http.HttpClient
import ru.skillbranch.sbdelivery.http.RestService
import ru.skillbranch.sbdelivery.http.NetworkManager
import ru.skillbranch.sbdelivery.http.data.dishes.DishRes
import ru.skillbranch.sbdelivery.orm.CartDao
import ru.skillbranch.sbdelivery.orm.CategoryDao
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.DishDao

object RootRepository {

    private val db: DeliveryDatabase by lazy {
        DeliveryDatabase.getInstance(SbDeliveryApplication.context)
    }

    private val categoryDao: CategoryDao by lazy { db.categoryDao() }
    private val dishDao: DishDao by lazy { db.dishDao() }
    private val cartDao: CartDao by lazy { db.cartDao() }

    private val service: RestService by lazy { NetworkManager.api }

    suspend fun isNeedUpdate(): Boolean {
        // если в БД ничего нет - обновление требуется
        dishDao.getFirstDish() ?: return true

        // если в БД блюдо есть, но на сервере есть более новое - обновление также требуется
        val dishes: List<DishRes> = service.dishes(0, 1)
        val firstDishRes: DishRes = dishes[0]
        val firstDish = dishDao.findDish(firstDishRes.id)
        return firstDish == null || firstDish.updatedAt < firstDishRes.updatedAt
    }

    @ExperimentalCoroutinesApi
    suspend fun sync() {
        val remoteCategories = HttpClient.getAllCategories()
        val remoteDishes = HttpClient.getAllDishes()
        val recommendedDishIds = HttpClient.getRecommendedIds().toHashSet()

        val localCategories = remoteCategories.map { it.toCategory() }
        val localDishes = remoteDishes.map { dishRes ->
            val dish = dishRes.toDish()
            if (recommendedDishIds.contains(dish.id)) { dish.recommended = true }
            dish
        }

        categoryDao.insert(localCategories)
        dishDao.insert(localDishes)
    }

}