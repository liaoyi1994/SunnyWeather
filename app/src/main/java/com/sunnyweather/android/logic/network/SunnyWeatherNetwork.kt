package com.sunnyweather.android.logic.network

/**
 * @Package:        com.example.sunnyweather.logic.network
 * @ClassName:      SunnyWeatherNetwork
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 15:57
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 15:57
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object SunnyWeatherNetwork {

    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query)

}