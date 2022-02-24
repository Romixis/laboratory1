package com.example.retrofitforecaster

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.ListAdapter
import java.security.spec.ECField

private const val FRAGMENT_TAG = "weatherFragment"

class MainActivity : AppCompatActivity() {
    private lateinit var contentfragment: weatherFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = ""

        if (savedInstanceState != null)
        {
            (supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_TAG) as
                    weatherFragment).also { contentfragment = it }
            contentfragment.restoreSavedWeather()
        }
        else {
            contentfragment = weatherFragment().apply {
                supportFragmentManager
                    .beginTransaction()
                    .add(this, FRAGMENT_TAG)
                    .commit()
            }
            contentfragment.getAllWeatherList("moscow")

        }

        //supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, weatherFragment()).commit()

        val button: Button = findViewById(R.id.search_button)
        button.setOnClickListener {
            val et: EditText = findViewById(R.id.search_edit_text)
            val city: String = et.text.toString()

            weatherFragment().search(city)
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        supportFragmentManager.putFragment(outState, FRAGMENT_TAG, contentfragment)
        supportFragmentManager.saveFragmentInstanceState(contentfragment)
        super.onSaveInstanceState(outState)
    }

}