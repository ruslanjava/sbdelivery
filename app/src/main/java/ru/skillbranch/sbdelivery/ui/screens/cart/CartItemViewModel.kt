package ru.skillbranch.sbdelivery.ui.screens.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
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

    fun cart(): LiveData<CartWithItems> {
        return cartDao.getCart()
    }

}