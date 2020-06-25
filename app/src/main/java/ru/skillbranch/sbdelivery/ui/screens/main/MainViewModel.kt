package ru.skillbranch.sbdelivery.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.skillbranch.sbdelivery.application.SbDeliveryApplication
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

class MainViewModel : ViewModel() {

    private val database: DeliveryDatabase = DeliveryDatabase.getInstance(SbDeliveryApplication.context)

    private val addedDishes: MutableLiveData<Dish> = MutableLiveData()
    private val clickedDishes: MutableLiveData<Dish> = MutableLiveData()

    private val menuItemClicks: MutableLiveData<MainMenuItem> = MutableLiveData()

    @ExperimentalCoroutinesApi
    fun popularDishes(): LiveData<List<Dish>> {
        return database.dishDao().getPopularDishes()
    }

    @ExperimentalCoroutinesApi
    fun recommendedDishes(): LiveData<List<Dish>> {
        return database.dishDao().getRecommendedDishes()
    }

    @ExperimentalCoroutinesApi
    fun bestDishes(): LiveData<List<Dish>> {
        return database.dishDao().getBestDishes()
    }

    fun addedDishes(): LiveData<Dish> {
        return addedDishes
    }

    fun clickedDishes(): LiveData<Dish> {
        return clickedDishes
    }

    fun menuItemClicks(): LiveData<MainMenuItem> {
        return menuItemClicks
    }

    fun handleAddClick(dish: Dish) {
        addedDishes.postValue(dish)
    }

    fun handleDishClick(dish: Dish) {
        clickedDishes.postValue(dish)
    }

    fun handleMainItemClick(menuItem: MainMenuItem) {
        menuItemClicks.postValue(menuItem)
    }

    enum class MainMenuItem {

        MAIN,
        MENU,
        FAVORITE,
        CART,
        PROFILE,
        ORDERS,
        NOTIFICATIONS,

        ABOUT

    }

}
