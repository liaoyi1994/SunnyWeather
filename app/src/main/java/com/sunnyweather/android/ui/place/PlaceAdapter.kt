package com.sunnyweather.android.ui.place

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.databinding.PlaceItemBinding
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment: PlaceFragment, private val places: List<Place>): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    lateinit var mBinding: PlaceItemBinding

    class ViewHolder(private val placeItemBinding: PlaceItemBinding): RecyclerView.ViewHolder(placeItemBinding.root){

        fun bind(place: Place){
            placeItemBinding.placeAddress.text = place.address
            placeItemBinding.placeName.text = place.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = PlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(mBinding)
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition
            val place = places[position]
            val activity = fragment.activity
            if (activity is WeatherActivity){
                activity.mBinding.drawerLayout.closeDrawers()
                activity.mViewModel.locationLng = place.location.lng
                activity.mViewModel.locationLat = place.location.lat
                activity.mViewModel.placeName = place.name
                activity.refreshWeather()
            }else {
                val intent = Intent(fragment.requireActivity(), WeatherActivity::class.java)
                intent.putExtra("location_lng", place.location.lng)
                intent.putExtra("location_lat", place.location.lat)
                intent.putExtra("place_name", place.name)
                fragment.startActivity(intent)
                fragment.activity?.finish()
            }
            fragment.mViewModel.savePlace(place)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    override fun getItemCount() :Int{
        return places.size
    }
}