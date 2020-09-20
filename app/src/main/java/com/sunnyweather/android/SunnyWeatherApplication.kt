package com.sunnyweather.android

import android.app.Application
import android.content.Context

/**
 * @Package:        com.example.sunnyweather
 * @ClassName:      SunnyWeatherApplication
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 15:21
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 15:21
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class SunnyWeatherApplication: Application(){

    companion object{
        const val TOKEN = "WLrlWqXsyMpNPkhn"
        lateinit var context: Context
    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }
}