package ru.skillbranch.sbdelivery.ui.screens.menu

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.skillbranch.sbdelivery.orm.CategoryDao
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.viewModel.BaseViewModel
import ru.skillbranch.sbdelivery.viewModel.Event
import javax.inject.Inject

class MenuViewModel : BaseViewModel() {

    @Inject
    lateinit var dishDao: DishDao

    @Inject
    lateinit var categoryDao: CategoryDao

    private val categoryClicks = MutableLiveData<Event<Category>>()
    private val dishClicks = MutableLiveData<Event<Dish>>()

    fun observeCategories(owner: LifecycleOwner, observer: Observer<List<Category>>) {
        val result = MutableLiveData<Event<List<Category>>>()
        launchSafely {
            val categories = mutableListOf<Category>()
            if (dishDao.getFirstSaleDish() != null) {
                categories.add(Category.SALES)
                result.postValue(Event(categories))
            }
            val rootCategories = categoryDao.getRootCategories()
            categories.addAll(rootCategories)
            result.postValue(Event(categories))
        }
        result.observeEvents(owner, observer)
    }

    fun observeCategoryClicks(owner: LifecycleOwner, observer: Observer<Category>) {
        categoryClicks.observeEvents(owner, observer)
    }

    fun handleCategoryClick(category: Category) {
        categoryClicks.postValue(Event(category))
    }

    fun observeDishClicks(owner: LifecycleOwner, observer: Observer<Dish>) {
        dishClicks.observeEvents(owner, observer)
    }

    fun handleDishClick(dish: Dish) {
        dishClicks.postValue(Event(dish))
    }

}