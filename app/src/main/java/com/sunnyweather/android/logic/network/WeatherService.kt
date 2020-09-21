package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.DailyResponse
import com.sunnyweather.android.logic.model.RealtimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Package:        com.sunnyweather.android.logic.network
 * @ClassName:      WeatherService
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 20:28
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 20:28
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
interface WeatherService{

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    suspend fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): RealtimeResponse

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    suspend fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): DailyResponse
}