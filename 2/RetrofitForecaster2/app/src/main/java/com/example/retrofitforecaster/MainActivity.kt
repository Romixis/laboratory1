package com.example.retrofitforecaster

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val  FRAGMENT_TAG = "weatherFragment"
class MainActivity : AppCompatActivity() {

    private lateinit var contentfragment: Retainragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null)
        {
            (supportFragmentManager.getFragment(
                    savedInstanceState,
                    FRAGMENT_TAG
            ) as
            Retainragment).also { contentfragment = it }
            contentfragment.restoreSavedWeather()
        }
        else {
            contentfragment = Retainragment().apply {
                supportFragmentManager
                    .beginTransaction()
                    .add(this, FRAGMENT_TAG)
                    .commit()
            }
            contentfragment.getAllWeatherList("Kemerovo")
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        supportFragmentManager.putFragment(outState, FRAGMENT_TAG, contentfragment)
        supportFragmentManager.saveFragmentInstanceState(contentfragment)
        super.onSaveInstanceState(outState)
    }
}

