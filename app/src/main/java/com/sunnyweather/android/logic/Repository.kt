package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

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
}