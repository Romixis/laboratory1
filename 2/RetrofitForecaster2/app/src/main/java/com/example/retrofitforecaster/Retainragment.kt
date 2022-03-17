package com.example.retrofitforecaster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val dataAdapter = Adapter()
var listweather = listOf<ListItem>()
class Retainragment : Fragment() {

    var mService: RetrofitServices = Common.retrofitService

    fun getAllWeatherList(city: String) {
        mService.getWeatherList(city).enqueue(object : Callback<DataWeather> {
            override fun onResponse(call: Call<DataWeather>, response: Response<DataWeather>) {
                val abc = response.body() as DataWeather
                listweather = abc.list
                Log.d("123", abc.cod)
                dataAdapter.submitList(listweather)
                dataAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<DataWeather>, t: Throwable) {
                Log.d("123", t.message.toString())
            }
        })
    }

    fun restoreSavedWeather(){
        dataAdapter.submitList(listweather)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRecyclerView(view)
    }

    private fun getRecyclerView(v: View){
        v.findViewById<RecyclerView>(R.id.rView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_retainragment, container, false)
    }
}