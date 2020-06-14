package ru.skillbranch.sbdelivery.orm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.skillbranch.sbdelivery.orm.entities.cart.Cart
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItem
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

@Database(entities = [
    Cart::class, CartItem::class,
    Category::class, Dish::class
], version = 1, exportSchema = false)
abstract class DeliveryDatabase: RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun dishDao(): DishDao

    fun dropDatabase() {
        cartDao().clearTables()
        dishDao().clearTables()
    }
    
    companion object {

        @Volatile
        private var INSTANCE: DeliveryDatabase? = null
        
        fun getInstance(context: Context): DeliveryDatabase {
            if (INSTANCE == null) {
                synchronized(DeliveryDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, DeliveryDatabase::class.java, "delivery.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
        
    }

}