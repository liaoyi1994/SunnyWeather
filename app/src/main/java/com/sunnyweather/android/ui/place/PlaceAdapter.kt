package com.sunnyweather.android.ui.place

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Place
import kotlinx.android.synthetic.main.place_item.view.*

/**
 * @Package:        com.example.sunnyweather.ui.place
 * @ClassName:      PlaceAdapter
 * @Description:    java类作用描述
 * @Author:         LY
 * @CreateDate:     2020/9/20 17:22
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/9/20 17:22
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false))

    override fun getItemCount(): Int{

        Log.e(TAG, "getItemCount: " + placeList.size )
        return placeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.itemView.placeName.text = place.name
        holder.itemView.placeAddress.text = place.address
    }

    companion object{
        private const val TAG = "PlaceAdapter"
    }
}