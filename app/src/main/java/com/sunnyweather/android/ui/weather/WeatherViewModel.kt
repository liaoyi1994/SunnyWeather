package com.sunnyweather.android.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Location
import com.sunnyweather.android.logic.model.Weather

/**
 * @Package:        com.sunnyweather.android.ui.weather
 * @ClassName:      WeatherViewModel
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 20:53
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 20:53
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class WeatherViewModel: ViewModel() {

    private val locationlLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationlLiveData){
        Repository.refreshWeather(it.lng, it.lat)
    }

    fun refreshWeather(lng: String, lat: String){
        locationlLiveData.value = Location(lng, lat)
    }
}