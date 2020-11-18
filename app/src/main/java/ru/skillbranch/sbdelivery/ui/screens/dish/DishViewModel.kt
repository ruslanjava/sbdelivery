package ru.skillbranch.sbdelivery.ui.screens.dish

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.skillbranch.sbdelivery.http.HttpClient
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.viewModel.BaseViewModel
import javax.inject.Inject

class DishViewModel : BaseViewModel() {

    @Inject
    lateinit var dishDao: DishDao

    fun observeDish(owner: LifecycleOwner, dishId: String, observer: Observer<Dish>) {
        dishDao.findDishLive(dishId).observe(owner, observer)
    }

    @ExperimentalCoroutinesApi
    fun observeComments(owner: LifecycleOwner, dishId: String, observer: Observer<List<ReviewRes>>) {
        val result = MutableLiveData<List<ReviewRes>>()
        launchSafely {
            val reviews = HttpClient.getAllReviews(dishId)
            result.postValue(reviews)
        }
        result.observe(owner, observer)
    }

    fun handleFavoriteClick(dishId: String) {
        launchSafely {
            dishDao.changeFavoriteState(dishId)
        }
    }

}