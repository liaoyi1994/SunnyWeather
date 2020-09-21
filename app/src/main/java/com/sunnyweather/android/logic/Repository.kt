package com.sunnyweather.android.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * @Package:        com.example.sunnyweather.logic
 * @ClassName:      Repository
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 16:05
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 16:05
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("Response status is ${placeResponse.status}"))
            }
        } catch (e: Exception){
            Result.failure<Exception>(e)
        }
        emit(result)
    }

    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO){
        val result = try {
            coroutineScope {
                /*val deferredRealtime =  async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val dailyResponse = deferredDaily.await()
                val realtimeResponse = deferredRealtime.await()*/
                val realtimeResponse = SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                val dailyResponse = SunnyWeatherNetwork.getDailyWeather(lng, lat)
                if(realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                } else {
                    Result.failure(
                        RuntimeException("Realtime response status is ${realtimeResponse.status}\r\nDaily response status is ${dailyResponse.status}")
                    )
                }
            }
        }catch (e: Exception){
            Result.failure<Exception>(e)
        }
        emit(result)
    }

    private const val TAG = "Repository"
}