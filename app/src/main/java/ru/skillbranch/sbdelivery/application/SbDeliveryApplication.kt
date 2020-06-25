package ru.skillbranch.sbdelivery.application

import android.app.Application
import android.content.Context

class SbDeliveryApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {

        lateinit var context: Context

    }

}