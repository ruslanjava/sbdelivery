package ru.skillbranch.sbdelivery.ui.screens.favorites

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.viewModel.BaseViewModel
import ru.skillbranch.sbdelivery.viewModel.Event
import javax.inject.Inject

class FavoritesViewModel : BaseViewModel() {

    @Inject
    lateinit var dishDao: DishDao

    private val addedDishes = MutableLiveData<Event<Dish>>()
    private val clickedDishes = MutableLiveData<Event<Dish>>()

    fun observeFavoriteDishes(owner: LifecycleOwner, observer: Observer<List<Dish>>) {
        dishDao.getFavoriteDishes().observe(owner, observer)
    }

    fun observeAddedDishes(owner: LifecycleOwner, observer: Observer<Dish>) {
        addedDishes.observeEvents(owner, observer)
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