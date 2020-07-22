package ru.skillbranch.sbdelivery.orm

import androidx.room.*
import ru.skillbranch.sbdelivery.orm.entities.cart.Cart
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItem
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItemFull
import ru.skillbranch.sbdelivery.orm.entities.cart.CartWithItems

@Dao
interface CartDao {

    @Transaction
    fun getFullCart(): CartWithItems? {
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
    fun insert(cart: Cart): Long {
        return insertCart(cart)
    }

    @Transaction
    fun clearTables() {
        deleteCartItems()
        deleteCart()
    }

    @Query("SELECT * FROM cart LIMIT 1")
    fun selectCart(): CartWithItems?

    @Query("SELECT * FROM CartItemFull")
    fun selectCartItems(): List<CartItemFull>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCart(cart: Cart): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItem(item: CartItem): Long

    @Update
    fun updateCart(cart: Cart)

    @Update
    fun updateCartItem(item: CartItem)

    @Query("DELETE FROM cart")
    fun deleteCart()

    @Query("DELETE FROM cart_item")
    fun deleteCartItems()

}