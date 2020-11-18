package ru.skillbranch.sbdelivery.application

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.skillbranch.sbdelivery.orm.OrmModule
import ru.skillbranch.sbdelivery.repository.RepositoryModule
import ru.skillbranch.sbdelivery.ui.UiModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    OrmModule::class,
    AppModule::class,
    RepositoryModule::class,
    UiModule::class
])
interface AppComponent: AndroidInjector<SbDeliveryApplication> {

    @Component.Factory
    abstract class Factory: AndroidInjector.Factory<SbDeliveryApplication>

}