package ru.skillbranch.sbdelivery.ui.screens.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbranch.sbdelivery.application.SbDeliveryApplication
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.repository.SingleLiveData

class FavoritesViewModel : ViewModel() {

    private val database: DeliveryDatabase by lazy{
        DeliveryDatabase.getInstance(SbDeliveryApplication.context)
    }
    private val dishDao: DishDao by lazy { database.dishDao() }

    private val addedDishes: SingleLiveData<Dish> = SingleLiveData()
    private val clickedDishes: SingleLiveData<Dish> = SingleLiveData()

    fun favoriteDishes(): LiveData<List<Dish>> {
        return dishDao.getFavoriteDishes()
    }

    fun addedDishes(): LiveData<Dish?> {
        return addedDishes
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