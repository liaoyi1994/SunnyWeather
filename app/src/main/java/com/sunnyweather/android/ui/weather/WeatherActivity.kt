package com.sunnyweather.android.ui.weather

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.sunnyweather.android.R
import com.sunnyweather.android.databinding.ActivityWeatherBinding
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity: AppCompatActivity() {

    private val mBinding by lazy {
        ActivityWeatherBinding.inflate(layoutInflater)
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 沉浸式
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        setContentView(mBinding.root)

        if (mViewModel.locationLng.isEmpty()){
            mViewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (mViewModel.locationLat.isEmpty()){
            mViewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (mViewModel.placeName.isEmpty()){
            mViewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        mViewModel.weatherLiveData.observe(this){result ->
            val weather = result.getOrNull()
            weather?.let {weather ->
                Log.e(TAG, "onCreate: 请求成功", )
                showWeatherInfo(weather)
            } ?: let {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
        Log.e(TAG, "onCreate: ${mViewModel.locationLng}   ${mViewModel.locationLat}" )
        mViewModel.refreshWeather(mViewModel.locationLng, mViewModel.locationLat)
    }


    private fun showWeatherInfo(weather: Weather){
        mBinding.now.placeName.text = mViewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        // 填充now.xml布局中数据
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        mBinding.now.currentTemp.text = currentTempText
        mBinding.now.currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        mBinding.now.currentAQI.text = currentPM25Text
        mBinding.now.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        // 填充forecast.xml布局中的数据
        mBinding.forecast.forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, mBinding.forecast.forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            Log.e(TAG, "showWeatherInfo: ${skycon.date}", )
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            mBinding.forecast.forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        mBinding.lifeIndex.coldRiskText.text = lifeIndex.coldRisk[0].desc
        mBinding.lifeIndex.dressingText.text = lifeIndex.dressing[0].desc
        mBinding.lifeIndex.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        mBinding.lifeIndex.carWashingText.text = lifeIndex.carWashing[0].desc
        mBinding.weatherLayout.visibility = View.VISIBLE
    }

    companion object{
        private const val TAG = "WeatherActivity"
    }
}