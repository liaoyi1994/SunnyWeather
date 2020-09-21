package com.sunnyweather.android.logic.model

/**
 * @Package:        com.sunnyweather.android.logic.model
 * @ClassName:      Weather
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 20:26
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 20:26
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)