package ru.skillbranch.sbdelivery.ui.screens.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    private val categoryData: SingleLiveData<Category> = SingleLiveData()

    private val addedDishes: SingleLiveData<Dish> = SingleLiveData()
    private val clickedDishes: SingleLiveData<Dish> = SingleLiveData()

    fun category(categoryId: String): SingleLiveData<Category> {
        viewModelScope.launch(Dispatchers.IO) {
            val category: Category
            if (categoryId == Category.SALES.id) {
                category = Category.SALES
            } else {
                category = categoryDao.getCategory(categoryId)
            }
            categoryData.postValue(category)
        }
        return categoryData
    }

    fun subCategories(categoryId: String): LiveData<List<Category>> {
        return categoryDao.getChildCategories(categoryId)
    }

    fun categoryDishes(categoryId: String): LiveData<List<Dish>> {
        val dishes : MediatorLiveData<List<Dish>> = MediatorLiveData()
        if (categoryId == Category.SALES.id) {
            dishes.addSource(dishDao.getSaleDishes()) { dishes.postValue(it) }
        } else {
            dishes.addSource(dishDao.getCategoryDishes(categoryId)) { dishes.postValue(it) }
        }
        return dishes
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