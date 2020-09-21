package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Package:        com.example.sunnyweather.logic.network
 * @ClassName:      PlaceService
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 15:45
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 15:45
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
interface PlaceService {

    /**
     * 获取城市数据
     */
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    suspend fun searchPlaces(@Query("query") query: String): PlaceResponse
}