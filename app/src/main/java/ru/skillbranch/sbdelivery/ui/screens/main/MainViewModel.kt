package ru.skillbranch.sbdelivery.ui.screens.main

import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.skillbranch.sbdelivery.http.HttpClient
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.viewModel.BaseViewModel
import ru.skillbranch.sbdelivery.viewModel.Event
import javax.inject.Inject

class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var dishDao: DishDao

    private val recommendedDishes = MediatorLiveData<List<Dish>>()

    private val addedDishes = MutableLiveData<Event<Dish>>()
    private val clickedDishes = MutableLiveData<Event<Dish>>()
    private val menuItemClicks = MutableLiveData<Event<MainMenuItem>>()

    @ExperimentalCoroutinesApi
    fun observePopularDishes(owner: LifecycleOwner, observer: Observer<List<Dish>>) {
        dishDao.getPopularDishes().observe(owner, observer)
    }

    @ExperimentalCoroutinesApi
    fun observeRecommendedDishes(owner: LifecycleOwner, observer: Observer<List<Dish>>) {
        recommendedDishes.addSource(dishDao.getRecommendedDishes()) {
            recommendedDishes.postValue(it)
        }
        launchSafely {
            // затем отдаем обновленный вариант
            val ids = HttpClient.getRecommendedIds()
            dishDao.updateRecommendedDishes(ids)
        }
        recommendedDishes.observe(owner, observer)
    }

    @ExperimentalCoroutinesApi
    fun observeBestDishes(owner: LifecycleOwner, observer: Observer<List<Dish>>) {
        dishDao.getBestDishes().observe(owner, observer)
    }

    fun observeAddedDishes(owner: LifecycleOwner, observer: Observer<Dish>) {
        addedDishes.observeEvents(owner, observer)
    }

    fun observeClickedDishes(owner: LifecycleOwner, observer: Observer<Dish>) {
        clickedDishes.observeEvents(owner, observer)
    }

    fun observeMenuItemClicks(owner: LifecycleOwner, observer: Observer<MainMenuItem>) {
        menuItemClicks.observeEvents(owner, observer)
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

    fun handleMainItemClick(menuItem: MainMenuItem) {
        menuItemClicks.postValue(Event(menuItem))
    }

}
