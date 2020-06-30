package ru.skillbranch.sbdelivery.ui.screens.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.skillbranch.sbdelivery.application.SbDeliveryApplication
import ru.skillbranch.sbdelivery.orm.CategoryDao
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.repository.SingleLiveData

class MenuViewModel : ViewModel() {

    private val database: DeliveryDatabase by lazy{
        DeliveryDatabase.getInstance(SbDeliveryApplication.context)
    }
    private val dishDao: DishDao by lazy { database.dishDao() }
    private val categoryDao: CategoryDao by lazy { database.categoryDao() }

    private val categoryClicks = SingleLiveData<Category>()
    private val dishClicks = SingleLiveData<Dish>()

    fun categories(): LiveData<List<Category>> {
        val result = MutableLiveData<List<Category>>()
        GlobalScope.launch(Dispatchers.IO) {
            val categories = mutableListOf<Category>()
            if (dishDao.getFirstSaleDish() != null) {
                categories.add(Category.SALES)
                result.postValue(categories)
            }
            val rootCategories = categoryDao.getRootCategories()
            categories.addAll(rootCategories)
            result.postValue(categories)
        }
        return result
    }

    fun categoryClicks(): LiveData<Category?> {
        return categoryClicks
    }

    fun handleCategoryClick(category: Category) {
        categoryClicks.postValue(category)
    }

    fun dishClicks(): LiveData<Dish?> {
        return dishClicks
    }

    fun handleDishClick(dish: Dish) {
        dishClicks.postValue(dish)
    }

}