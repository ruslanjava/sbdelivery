package ru.skillbranch.sbdelivery.orm

import androidx.room.*
import ru.skillbranch.sbdelivery.orm.entities.cart.Cart
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItem
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItemFull
import ru.skillbranch.sbdelivery.orm.entities.cart.CartWithItems

@Dao
abstract class CartDao {

    @Transaction
    open fun getFullCart(): CartWithItems? {
        val cart = selectCart() ?: return null
        val items = selectCartItems()
        cart.items = items
        return cart
    }

    @Transaction
    fun upsertCartItem(id: String, amount: Int) {
        val cartItem = CartItem()
        cartItem.id = id
        cartItem.amount = amount
        insertCartItem(cartItem)
    }

    @Transaction
    open fun insert(cart: Cart): Long {
        return insertCart(cart)
    }

    @Transaction
    open fun clearTables() {
        deleteCartItems()
        deleteCart()
    }

    @Transaction
    @Query("SELECT * FROM cart LIMIT 1")
    abstract fun selectCart(): CartWithItems?

    @Transaction
    @Query("SELECT * FROM cart_item")
    abstract fun selectCartItems(): List<CartItemFull>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertCart(cart: Cart): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertCartItem(item: CartItem): Long

    @Update
    protected abstract fun updateCart(cart: Cart)

    @Update
    protected abstract fun updateCartItem(item: CartItem): Long

    @Query("DELETE FROM cart")
    protected abstract fun deleteCart()

    @Query("DELETE FROM cart_item")
    protected abstract fun deleteCartItems()

}