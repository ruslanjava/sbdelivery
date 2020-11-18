package ru.skillbranch.sbdelivery.application

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SbDeliveryApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    companion object {

        lateinit var context: Context

    }

}