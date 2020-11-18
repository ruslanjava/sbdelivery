package ru.skillbranch.sbdelivery.application

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ru.skillbranch.sbdelivery.http.BuildConfig
import ru.skillbranch.sbdelivery.http.NetworkMonitor
import timber.log.Timber

class SbDeliveryApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        context = this

        // start network monitoring
        NetworkMonitor.registerNetworkMonitor(applicationContext)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler(defaultHandler))
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    companion object {

        lateinit var context: Context

    }

}