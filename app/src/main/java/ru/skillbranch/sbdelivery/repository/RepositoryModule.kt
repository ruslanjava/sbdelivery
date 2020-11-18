package ru.skillbranch.sbdelivery.repository

import dagger.Module
import dagger.Provides
import ru.skillbranch.sbdelivery.orm.CartDao
import ru.skillbranch.sbdelivery.orm.CategoryDao
import ru.skillbranch.sbdelivery.orm.DishDao
import ru.skillbranch.sbdelivery.repository.root.RootRepository

@Module
class RepositoryModule {

    @Provides
    fun providesRootRepository(
        categoryDao: CategoryDao, dishDao: DishDao, cartDao: CartDao
    ): RootRepository {
        return RootRepository(categoryDao, dishDao, cartDao)
    }

}