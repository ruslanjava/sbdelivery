package ru.skillbranch.sbdelivery.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.skillbranch.sbdelivery.ui.screens.RootActivity
import ru.skillbranch.sbdelivery.ui.screens.RootViewModel

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun provideRootActivity(): RootActivity
    @ContributesAndroidInjector
    abstract fun provideRootViewModel(): RootViewModel

}