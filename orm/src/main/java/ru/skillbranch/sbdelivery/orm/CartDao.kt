package ru.skillbranch.sbdelivery.orm

import androidx.room.*
import ru.skillbranch.sbdelivery.orm.entities.cart.Cart
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItem

@Dao
abstract class CartDao {

    @Transaction
    open fun getCart(): Cart? {
        val cart: Cart = selectCart() ?: return null
        val items: List<CartItem> = selectCartItems()
        cart.items = items
        return cart
    }

    @Transaction
    open fun insert(cart: Cart): Long {
        val cartId = insertCart(cart)
        cart.items.forEach {
            insertCartItem(it)
        }
        return cartId
    }

    @Transaction
    open fun update(cart: Cart) {
        updateCart(cart)
        deleteCartItems()
        cart.items.forEach {
            insertCartItem(it)
        }
    }

    @Transaction
    open fun clearTables() {
        deleteCartItems()
        deleteCart()
    }

    @Update
    protected abstract fun updateCart(cart: Cart)

    @Query("SELECT * FROM cart LIMIT 1")
    protected abstract fun selectCart(): Cart?

    @Query("SELECT * FROM cart_item")
    protected abstract fun selectCartItems(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertCart(cart: Cart): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertCartItem(cartItem: CartItem): Long

    @Query("DELETE FROM cart")
    protected abstract fun deleteCart()

    @Query("DELETE FROM cart_item")
    protected abstract fun deleteCartItems()

}