package com.sunnyweather.android.ui.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.TextWatcherAdapter
import com.sunnyweather.android.databinding.FragmentPlaceBinding

class PlaceFragment: Fragment() {

    private var mBinding: FragmentPlaceBinding? = null

    private val mViewModel: PlaceViewModel by lazy {
        ViewModelProviders.of(this)[PlaceViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPlaceBinding.inflate(layoutInflater)
        return mBinding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val placeAdapter = PlaceAdapter(mViewModel.placeList)
        mBinding?.recyclerView?.adapter = placeAdapter
        mBinding?.searchPlaceEdit?.addTextChangedListener {editable ->
            val content = editable.toString()
            if (content.isNotEmpty()){
                mViewModel.searchPlaces(content)
            }else {
                mBinding?.recyclerView?.visibility = View.GONE
                mBinding?.bgImageView?.visibility = View.VISIBLE
                mViewModel.placeList.clear()
                placeAdapter.notifyDataSetChanged()
            }
        }


        mViewModel.placeLiveData.observe(this, { result ->
            val places = result.getOrNull()
            places?.let { places ->
                Log.e(TAG, "onActivityCreated: ${places}" )
                mBinding?.recyclerView?.visibility = View.VISIBLE
                mBinding?.bgImageView?.visibility = View.GONE
                mViewModel.placeList.clear()
                mViewModel.placeList.addAll(places)
                placeAdapter.notifyDataSetChanged()
            }?: let {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
    companion object{
        private const val TAG = "PlaceFragment"
    }
}