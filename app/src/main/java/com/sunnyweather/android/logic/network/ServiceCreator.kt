package com.sunnyweather.android.logic.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Package:        com.example.sunnyweather.logic.network
 * @ClassName:      ServiceCreator
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 15:52
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 15:52
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private const val TAG = "ServiceCreator"

    /*
      **打印retrofit信息部分
     */
    private val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
        override fun log(message: String) {
            Log.d(TAG, message)
        }

    })


    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified  T> create(): T = create(T::class.java)
}