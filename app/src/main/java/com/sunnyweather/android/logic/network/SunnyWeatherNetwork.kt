package com.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {

    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()

    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()



    private suspend fun <T> Call<T>.await(): T{

        // 清除回调
        return suspendCoroutine { contination ->
            enqueue(object: Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    var body = response.body()
                    if (body != null) contination.resume(body)
                    else contination.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    contination.resumeWithException(t)
                }
            })
        }
    }
}