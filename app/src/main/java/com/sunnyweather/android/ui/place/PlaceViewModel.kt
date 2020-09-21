package com.sunnyweather.android.ui.place

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place

/**
 * @Package:        com.example.sunnyweather.ui.place
 * @ClassName:      PlaceViewModel
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 17:15
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 17:15
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class PlaceViewModel: ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData){
        Repository.searchPlaces(it)
    }

    fun searchPlaces(query: String){
        searchLiveData.value = query
    }

    companion object{
        private const val TAG = "PlaceViewModel"
    }

}