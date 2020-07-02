package ru.skillbranch.sbdelivery.ui.screens.dish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.skillbranch.sbdelivery.application.SbDeliveryApplication
import ru.skillbranch.sbdelivery.http.HttpClient
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

class DishViewModel : ViewModel() {

    private val database: DeliveryDatabase by lazy{
        DeliveryDatabase.getInstance(SbDeliveryApplication.context)
    }
    private val dishDao: DishDao by lazy { database.dishDao() }

    private val dishes: MutableLiveData<Dish> = MutableLiveData()

    fun dish(dishId: String): LiveData<Dish> {
        viewModelScope.launch(Dispatchers.IO) {
            val dish = dishDao.findDish(dishId)
            dishes.postValue(dish)
        }
        return dishes
    }

    @ExperimentalCoroutinesApi
    fun comments(dishId: String): LiveData<List<ReviewRes>> {
        val result = MutableLiveData<List<ReviewRes>>()
        viewModelScope.launch(Dispatchers.IO) {
            val reviews = HttpClient.getAllReviews(dishId)
            result.postValue(reviews)
        }
        return result
    }

    fun handleFavoriteClick(dishId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dishDao.changeFavoriteState(dishId)
            val dish = dishDao.findDish(dishId)
            dishes.postValue(dish)
        }
    }

}