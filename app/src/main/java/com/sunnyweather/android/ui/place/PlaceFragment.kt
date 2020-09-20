package com.sunnyweather.android.ui.place

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Place
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * @Package:        com.example.sunnyweather.ui.place
 * @ClassName:      PlaceFragment
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 17:27
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 17:27
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class PlaceFragment: Fragment() {

    private val mViewModel by lazy {
        ViewModelProvider(this)[PlaceViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_place, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PlaceAdapter(this@PlaceFragment, mViewModel.placeList)
        }
        searchPlaceEdit.addTextChangedListener {
            val content = it.toString()
            if(content.isNotEmpty()){
                mViewModel.searchPlaces(content)
            }else{
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                mViewModel.placeList.clear()
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        mViewModel.placeLiveData.observe(viewLifecycleOwner, Observer {
            val places = it.getOrNull()
            Log.e(TAG, "onActivityCreated: " + places.toString() )
            if(places != null){
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                mViewModel.placeList.clear()
                mViewModel.placeList.addAll(places as Collection<Place>)
                recyclerView.adapter?.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    companion object{
        private const val TAG = "PlaceFragment"
    }
}