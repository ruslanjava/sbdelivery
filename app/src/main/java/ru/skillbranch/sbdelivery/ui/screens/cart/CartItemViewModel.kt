package ru.skillbranch.sbdelivery.ui.screens.cart

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.skillbranch.sbdelivery.orm.CartDao
import ru.skillbranch.sbdelivery.orm.entities.cart.CartWithItems
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.viewModel.BaseViewModel
import ru.skillbranch.sbdelivery.viewModel.Event
import javax.inject.Inject

class CartItemViewModel : BaseViewModel() {

    @Inject
    lateinit var cartDao: CartDao

    private val cartLiveData = MutableLiveData<Event<CartWithItems>>()

    fun observeCart(owner: LifecycleOwner, observer: Observer<CartWithItems>) {
        launchSafely {
            val cart = cartDao.getFullCart()
            if (cart != null) {
                cartLiveData.postValue(Event(cart))
            }
        }
        cartLiveData.observeEvents(owner, observer)
    }

    fun handleDishAmountChange(dish: Dish, amount: Int) {
        launchSafely {
            cartDao.upsertCartItem(dish.id, amount)
            val cart = cartDao.getFullCart()
            if (cart != null) {
                cartLiveData.postValue(Event(cart))
            }
        }
    }

}