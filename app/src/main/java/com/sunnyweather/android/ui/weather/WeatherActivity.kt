package com.sunnyweather.android.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.model.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Package:        com.sunnyweather.android.ui.weather
 * @ClassName:      WeatherActivity
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 21:04
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 21:04
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */

class WeatherActivity : AppCompatActivity(){

    val mViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_weather)
        if(mViewModel.locationLng.isEmpty()){
            mViewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if(mViewModel.locationLat.isEmpty()){
            mViewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if(mViewModel.placeName.isEmpty()){
            mViewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        mViewModel.weatherLiveData.observe(this, Observer {
            val weather = it.getOrNull()
            if(weather != null){
                showWeatherInfo(weather as Weather)
            }else{
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        })
        mViewModel.refreshWeather(mViewModel.locationLng, mViewModel.locationLat)
    }

    private fun showWeatherInfo(weather: Weather){
        placeName.text = mViewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        // 填充now.xml布局中数据
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        // 填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }

    companion object{
        private const val TAG = "WeatherActivity"
    }
}