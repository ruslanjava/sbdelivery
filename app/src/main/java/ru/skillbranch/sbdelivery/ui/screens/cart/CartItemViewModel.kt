package ru.skillbranch.sbdelivery.ui.screens.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.skillbranch.sbdelivery.application.SbDeliveryApplication
import ru.skillbranch.sbdelivery.orm.CartDao
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.entities.cart.CartWithItems
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

class CartItemViewModel : ViewModel() {

    private val database: DeliveryDatabase by lazy {
        DeliveryDatabase.getInstance(SbDeliveryApplication.context)
    }
    private val cartDao: CartDao by lazy { database.cartDao() }

    private val cartLiveData: MutableLiveData<CartWithItems> = MutableLiveData()

    fun cart(): LiveData<CartWithItems?> {
        viewModelScope.launch(Dispatchers.IO) {
            val cart = cartDao.getFullCart()
            cartLiveData.postValue(cart)
        }
        return cartLiveData
    }

    fun handleDishAmountChange(dish: Dish, amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cartDao.upsertCartItem(dish.id, amount)
            val cart = cartDao.getFullCart()
            cartLiveData.postValue(cart)
        }
    }

}