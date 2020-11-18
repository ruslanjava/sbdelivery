package ru.skillbranch.sbdelivery.application

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesContext(application: SbDeliveryApplication): Context {
        return application
    }

}