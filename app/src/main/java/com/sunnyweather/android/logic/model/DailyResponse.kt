package com.sunnyweather.android.logic.model

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @Package:        com.sunnyweather.android.logic.model
 * @ClassName:      DailyResponse
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 20:16
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 20:16
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class DailyResponse(val status: String, val result: Result) {

    data class Result(val daily: Daily)

    data class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>, @SerializedName("life_index") val lifeIndex: LifeIndex)

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: Date)

    data class LifeIndex(val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>, val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)

    data class LifeDescription(val desc: String)

}