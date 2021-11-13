package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        sContext = applicationContext
    }

    @SuppressLint("StaticFieldLeak")
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var sContext: Context
        // const val TOKEN = "WLrlWqXsyMpNPkhn"
        const val TOKEN = "F6FpeVaXQXs9T3A9"
    }
}