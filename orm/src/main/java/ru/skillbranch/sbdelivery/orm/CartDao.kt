package ru.skillbranch.sbdelivery.orm

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.skillbranch.sbdelivery.orm.entities.cart.Cart
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItem
import ru.skillbranch.sbdelivery.orm.entities.cart.CartWithItems

@Dao
abstract class CartDao {

    @Transaction
    @Query("SELECT * FROM cart LIMIT 1")
    abstract fun getCart(): LiveData<CartWithItems>

    @Transaction
    open fun insert(cart: CartWithItems): Long {
        val cartId = insertCart(cart)
        cart.items.forEach {
            it.cartId = cartId
            insertCartItem(it)
        }
        return cartId
    }

    @Transaction
    open fun updateCart(cart: CartWithItems) {
        updateCart(cart)
        deleteCartItems(cart.id)
        cart.items.forEach {
            insertCartItem(it)
        }
    }

    @Transaction
    open fun clearTables() {
        deleteCartItems()
        deleteCart()
    }

    @Transaction
    @Query("SELECT * FROM cart LIMIT 1")
    abstract fun selectCart(): CartWithItems?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertCart(cart: Cart): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertCartItem(item: CartItem): Long

    @Update
    protected abstract fun updateCart(cart: Cart)

    @Query("DELETE FROM cart")
    protected abstract fun deleteCart()

    @Query("DELETE FROM cart_item")
    protected abstract fun deleteCartItems()

    @Query("DELETE FROM cart_item WHERE cart_id = :cartId")
    protected abstract fun deleteCartItems(cartId: Long)

}