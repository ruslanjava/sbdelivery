package ru.skillbranch.sbdelivery.orm

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class OrmModule {

    @Provides
    @Singleton
    fun providesDeliveryDatabase(context: Context): DeliveryDatabase {
        return Room.databaseBuilder(
            context.applicationContext, DeliveryDatabase::class.java, "delivery.db"
        ).build()
    }

    @Provides
    fun providesCartDao(deliveryDatabase: DeliveryDatabase): CartDao {
        return deliveryDatabase.cartDao()
    }

    @Provides
    fun providesCategoryDao(deliveryDatabase: DeliveryDatabase): CategoryDao {
        return deliveryDatabase.categoryDao()
    }

    @Provides
    fun providesDishDao(deliveryDatabase: DeliveryDatabase): DishDao {
        return deliveryDatabase.dishDao()
    }

}

