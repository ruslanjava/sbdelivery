package ru.skillbranch.sbdelivery.orm

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import ru.skillbranch.sbdelivery.orm.entities.cart.Cart
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItem

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CartInstrumentedTest {

    @Test
    fun testCartDao() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val cartDao = DeliveryDatabase.getInstance(appContext).cartDao()
        cartDao.clearTables()

        val cart = newCart("123456", "PROMO", 2000)
        cart.items = listOf(
            newCartItem("1", 1, 150),
            newCartItem("2", 3, 450),
            newCartItem("3", 10, 1500)
        )
        cartDao.insert(cart)

        val newCart = cartDao.getCart()
        assertThat(newCart, CoreMatchers.notNullValue())

        assertThat(newCart!!.promocode, CoreMatchers.`is`("123456"))
        assertThat(newCart.promotext, CoreMatchers.`is`("PROMO"))
        assertThat(newCart.total, CoreMatchers.`is`(2000))

        val items = newCart.items
        assertThat(items.size, CoreMatchers.`is`(3))
    }

    private fun newCart(promocode: String, promotext: String, total: Int): Cart {
        val cart = Cart()
        cart.promocode = promocode
        cart.promotext = promotext
        cart.total = total
        return cart
    }

    private fun newCartItem(dishId: String, amount: Int, number: Int): CartItem {
        val cartItem = CartItem()
        cartItem.dishId = dishId
        cartItem.amount = amount
        cartItem.number = number
        return cartItem
    }

}