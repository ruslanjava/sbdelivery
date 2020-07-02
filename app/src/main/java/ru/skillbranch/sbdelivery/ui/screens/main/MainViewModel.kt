package ru.skillbranch.sbdelivery.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
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
    private val bestDishes: MediatorLiveData<List<Dish>> = MediatorLiveData()
    private val popularDishes: MediatorLiveData<List<Dish>> = MediatorLiveData()

    private val addedDishes: SingleLiveData<Dish> = SingleLiveData()
    private val clickedDishes: SingleLiveData<Dish> = SingleLiveData()
    private val menuItemClicks: SingleLiveData<MainMenuItem> = SingleLiveData()

    @ExperimentalCoroutinesApi
    fun popularDishes(): LiveData<List<Dish>> {
        popularDishes.addSource(dishDao.getPopularDishes(), Observer { popularDishes.postValue(it) })
        return popularDishes
    }

    @ExperimentalCoroutinesApi
    fun recommendedDishes(): LiveData<List<Dish>> {
        recommendedDishes.addSource(dishDao.getRecommendedDishes(), Observer { recommendedDishes.postValue(it) })
        viewModelScope.launch(Dispatchers.IO) {
            // затем отдаем обновленный вариант
            val ids = HttpClient.getRecommendedIds()
            dishDao.updateRecommendedDishes(ids)
            MainScope().launch {
                recommendedDishes.addSource(dishDao.getRecommendedDishes(), Observer { recommendedDishes.postValue(it) })
            }
        }
        return recommendedDishes
    }

    @ExperimentalCoroutinesApi
    fun bestDishes(): LiveData<List<Dish>> {
        bestDishes.addSource(dishDao.getBestDishes(), Observer { bestDishes.postValue(it) })
        return bestDishes
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
            dishDao.changeFavoriteState(dish.id)
            MainScope().launch {
                popularDishes.addSource(dishDao.getPopularDishes(), Observer { popularDishes.postValue(it) })
                recommendedDishes.addSource(dishDao.getRecommendedDishes(), Observer { recommendedDishes.postValue(it) })
                bestDishes.addSource(dishDao.getBestDishes(), Observer { bestDishes.postValue(it) })
            }
        }
    }

    fun handleMainItemClick(menuItem: MainMenuItem) {
        menuItemClicks.postValue(menuItem)
    }

}
