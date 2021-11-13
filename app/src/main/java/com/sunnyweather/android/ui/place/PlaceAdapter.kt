package com.sunnyweather.android.ui.place

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.databinding.PlaceItemBinding
import com.sunnyweather.android.logic.model.Place

class PlaceAdapter(private val places: List<Place>): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    lateinit var mBinding: PlaceItemBinding

    class ViewHolder(private val placeItemBinding: PlaceItemBinding): RecyclerView.ViewHolder(placeItemBinding.root){

        fun bind(place: Place){
            placeItemBinding.placeAddress.text = place.address
            placeItemBinding.placeName.text = place.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = PlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    override fun getItemCount() :Int{
        return places.size
    }
}