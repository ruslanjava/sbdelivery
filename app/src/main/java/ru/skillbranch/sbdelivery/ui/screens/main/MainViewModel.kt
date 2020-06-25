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

    fun addedDishes(): MutableLiveData<Dish> {
        return addedDishes
    }

    fun clickedDishes(): MutableLiveData<Dish> {
        return clickedDishes
    }

    fun handleAddClick(dish: Dish) {
        addedDishes.postValue(dish)
    }

    fun handleDishClick(dish: Dish) {
        clickedDishes.postValue(dish)
    }

}
