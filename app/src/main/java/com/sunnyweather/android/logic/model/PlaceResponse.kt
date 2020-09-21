package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @Package:        com.example.sunnyweather.logic.model
 * @ClassName:      PlaceResponse
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 15:38
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 15:38
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)

data class Location(val lng: String, val lat: String)