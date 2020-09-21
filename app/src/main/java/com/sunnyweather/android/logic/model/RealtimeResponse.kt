package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @Package:        com.sunnyweather.android.logic.model
 * @ClassName:      RecaltimeResponse
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 20:09
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 20:09
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
/*data class RealtimeResponse(val status: String, val result: Result){
    data class Result(val realtime: Realtime)

    data class Realtime(val temperature: Float, val skycon: String, @SerializedName("air_quality") val airQuality: AirQuality)

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}*/


data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(val skycon: String, val temperature: Float, @SerializedName("air_quality") val airQuality: AirQuality)

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)

}

