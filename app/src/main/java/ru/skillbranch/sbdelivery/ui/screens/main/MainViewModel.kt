package ru.skillbranch.sbdelivery.ui.screens.main

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.skillbranch.sbdelivery.application.SbDeliveryApplication
import ru.skillbranch.sbdelivery.http.HttpClient
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.repository.SingleLiveData

internal class MainViewModel : ViewModel() {

    private val database: DeliveryDatabase by lazy {
        DeliveryDatabase.getInstance(SbDeliveryApplication.context)
    }
    private val dishDao: DishDao by lazy { database.dishDao() }

    private val recommendedDishes: MediatorLiveData<List<Dish>> = MediatorLiveData()

    private val addedDishes: SingleLiveData<Dish> = SingleLiveData()
    private val clickedDishes: SingleLiveData<Dish> = SingleLiveData()
    private val menuItemClicks: SingleLiveData<MainMenuItem> = SingleLiveData()

    @ExperimentalCoroutinesApi
    fun popularDishes(): LiveData<List<Dish>> {
        return dishDao.getPopularDishes()
    }

    @ExperimentalCoroutinesApi
    fun recommendedDishes(): LiveData<List<Dish>> {
        recommendedDishes.addSource(dishDao.getRecommendedDishes(), Observer {
            recommendedDishes.postValue(it)
        })
        viewModelScope.launch(Dispatchers.IO) {
            // затем отдаем обновленный вариант
            val ids = HttpClient.getRecommendedIds()
            dishDao.updateRecommendedDishes(ids)
        }
        return recommendedDishes
    }

    @ExperimentalCoroutinesApi
    fun bestDishes(): LiveData<List<Dish>> {
        return dishDao.getBestDishes()
    }

    fun addedDishes(): LiveData<Dish?> {
        return addedDishes
    }

    fun clickedDishes(): LiveData<Dish?> {
        return clickedDishes
    }

    fun menuItemClicks(): LiveData<MainMenuItem?> {
        return menuItemClicks
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

    fun handleMainItemClick(menuItem: MainMenuItem) {
        menuItemClicks.postValue(menuItem)
    }

}
