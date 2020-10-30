package ru.skillbranch.sbdelivery.http

import android.app.Application
import android.content.Context

class HttpClientApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        // start network monitoring
        NetworkMonitor.registerNetworkMonitor(applicationContext)
    }

    companion object {
        private var instance: HttpClientApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}