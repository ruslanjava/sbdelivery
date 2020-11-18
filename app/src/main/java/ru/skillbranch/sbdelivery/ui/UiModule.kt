package ru.skillbranch.sbdelivery.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.skillbranch.sbdelivery.ui.screens.RootActivity
import ru.skillbranch.sbdelivery.ui.screens.RootViewModel
import ru.skillbranch.sbdelivery.ui.screens.cart.CartFragment
import ru.skillbranch.sbdelivery.ui.screens.cart.CartItemViewModel
import ru.skillbranch.sbdelivery.ui.screens.category.CategoryFragment
import ru.skillbranch.sbdelivery.ui.screens.category.CategoryViewModel
import ru.skillbranch.sbdelivery.ui.screens.dish.DishFragment
import ru.skillbranch.sbdelivery.ui.screens.dish.DishViewModel
import ru.skillbranch.sbdelivery.ui.screens.favorites.FavoritesFragment
import ru.skillbranch.sbdelivery.ui.screens.favorites.FavoritesViewModel
import ru.skillbranch.sbdelivery.ui.screens.main.MainFragment
import ru.skillbranch.sbdelivery.ui.screens.main.MainViewModel
import ru.skillbranch.sbdelivery.ui.screens.menu.MenuFragment
import ru.skillbranch.sbdelivery.ui.screens.menu.MenuViewModel

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun provideRootActivity(): RootActivity
    @ContributesAndroidInjector
    abstract fun provideRootViewModel(): RootViewModel

    @ContributesAndroidInjector
    abstract fun provideCartFragment(): CartFragment
    @ContributesAndroidInjector
    abstract fun provideCartViewModel(): CartItemViewModel

    @ContributesAndroidInjector
    abstract fun provideCategoryFragment(): CategoryFragment
    @ContributesAndroidInjector
    abstract fun provideCategoryViewModel(): CategoryViewModel

    @ContributesAndroidInjector
    abstract fun provideDishFragment(): DishFragment
    @ContributesAndroidInjector
    abstract fun provideDishViewModel(): DishViewModel

    @ContributesAndroidInjector
    abstract fun provideFavoritesFragment(): FavoritesFragment
    @ContributesAndroidInjector
    abstract fun provideFavoritesViewModel(): FavoritesViewModel

    @ContributesAndroidInjector
    abstract fun provideMainFragment(): MainFragment
    @ContributesAndroidInjector
    abstract fun provideMainViewModel(): MainViewModel

    @ContributesAndroidInjector
    abstract fun provideMenuFragment(): MenuFragment
    @ContributesAndroidInjector
    abstract fun provideMenuViewModel(): MenuViewModel

}