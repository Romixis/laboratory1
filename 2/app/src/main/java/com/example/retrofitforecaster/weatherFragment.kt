package com.example.retrofitforecaster

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val dataAdapter by lazy { Adapter() }
var listweather = listOf<ListItem>()

class weatherFragment() : Fragment() {

    var mService: RetrofitServices = Common.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRecyclerView(view)
        //getAllWeatherList("moscow")
    }

    private fun getRecyclerView(v: View){
        v.findViewById<RecyclerView>(R.id.rView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = dataAdapter
        }
    }

    fun getAllWeatherList(city: String) {
        mService.getWeatherList(city).enqueue(object : Callback<DataWeather> {
            override fun onResponse(call: Call<DataWeather>, response: Response<DataWeather>) {
                val abc = response.body() as DataWeather
                listweather = abc.list
                Log.d("список", listweather.toString())
                dataAdapter.submitList(listweather)
                dataAdapter.notifyDataSetChanged()

            }
            override fun onFailure(call: Call<DataWeather>, t: Throwable) {
                Log.d("фейл", t.message.toString())
            }
        })

    }

    fun search(city: String) {
        Log.d("поиск", "Вызван")
        listweather = emptyList()
        getAllWeatherList(city)
        dataAdapter.submitList(listweather)
        dataAdapter.notifyDataSetChanged()
    }

    fun restoreSavedWeather()
    {
        dataAdapter.submitList(listweather)
    }

}