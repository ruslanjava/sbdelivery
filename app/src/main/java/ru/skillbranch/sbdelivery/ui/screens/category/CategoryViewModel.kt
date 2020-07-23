package ru.skillbranch.sbdelivery.ui.screens.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbranch.sbdelivery.application.SbDeliveryApplication
import ru.skillbranch.sbdelivery.orm.CategoryDao
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.repository.SingleLiveData

class CategoryViewModel : ViewModel() {

    private val database: DeliveryDatabase by lazy {
        DeliveryDatabase.getInstance(SbDeliveryApplication.context)
    }
    private val dishDao: DishDao by lazy { database.dishDao() }
    private val categoryDao: CategoryDao by lazy { database.categoryDao() }

    private val addedDishes: SingleLiveData<Dish> = SingleLiveData()
    private val clickedDishes: SingleLiveData<Dish> = SingleLiveData()

    fun category(categoryId: String): LiveData<Category> {
        return categoryDao.getCategory(categoryId)
    }

    fun subCategories(categoryId: String): LiveData<List<Category>> {
        return categoryDao.getChildCategories(categoryId)
    }

    fun categoryDishes(categoryId: String): LiveData<List<Dish>> {
        return dishDao.getCategoryDishes(categoryId)
    }

    fun clickedDishes(): LiveData<Dish?> {
        return clickedDishes
    }

    fun handleAddClick(dish: Dish) {
        addedDishes.postValue(dish)
    }

    fun handleDishClick(dish: Dish) {
        clickedDishes.postValue(dish)
    }

    fun handleFavoriteClick(dish: Dish) {
        viewModelScope.launch(Dispatchers.IO) {
            val dishId = dish.id
            dishDao.changeFavoriteState(dishId)
        }
    }

}