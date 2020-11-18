package ru.skillbranch.sbdelivery.ui.screens.category

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.skillbranch.sbdelivery.orm.CategoryDao
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.repository.SingleLiveData
import ru.skillbranch.sbdelivery.viewModel.BaseViewModel
import ru.skillbranch.sbdelivery.viewModel.Event
import javax.inject.Inject

class CategoryViewModel : BaseViewModel() {

    @Inject
    lateinit var dishDao: DishDao

    @Inject
    lateinit var categoryDao: CategoryDao

    private val categoryData = MutableLiveData<Event<Category>>()

    private val addedDishes = MutableLiveData<Event<Dish>>()
    private val clickedDishes = MutableLiveData<Event<Dish>>()

    fun observeCategory(owner: LifecycleOwner, categoryId: String, observer: Observer<Category>) {
        launchSafely {
            val category: Category
            if (categoryId == Category.SALES.id) {
                category = Category.SALES
            } else {
                category = categoryDao.getCategory(categoryId)
            }
            categoryData.postValue(Event(category))
        }
        categoryData.observeEvents(owner, observer)
    }

    fun observeSubCategories(owner: LifecycleOwner, categoryId: String, observer: Observer<List<Category>>) {
        val liveData = categoryDao.getChildCategories(categoryId)
        liveData.observe(owner, observer)
    }

    fun observeCategoryDishes(owner: LifecycleOwner, categoryId: String, observer: Observer<List<Dish>>) {
        val dishes : MediatorLiveData<List<Dish>> = MediatorLiveData()
        if (categoryId == Category.SALES.id) {
            dishes.addSource(dishDao.getSaleDishes()) { dishes.postValue(it) }
        } else {
            dishes.addSource(dishDao.getCategoryDishes(categoryId)) { dishes.postValue(it) }
        }
        dishes.observe(owner, observer)
    }

    fun observeClickedDishes(owner: LifecycleOwner, observer: Observer<Dish>) {
        clickedDishes.observeEvents(owner, observer)
    }

    fun handleAddClick(dish: Dish) {
        addedDishes.postValue(Event(dish))
    }

    fun handleDishClick(dish: Dish) {
        clickedDishes.postValue(Event(dish))
    }

    fun handleFavoriteClick(dish: Dish) {
        launchSafely {
            val dishId = dish.id
            dishDao.changeFavoriteState(dishId)
        }
    }

}