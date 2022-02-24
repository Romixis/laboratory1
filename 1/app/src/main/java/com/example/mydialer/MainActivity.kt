package com.example.mydialer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonArray
import timber.log.Timber
import android.view.*
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import java.net.HttpURLConnection
import java.net.URL
import androidx.recyclerview.widget.ListAdapter



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        title = ""
        loadData()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView2, ContactFragment()).commit()

        val edtxtx_search: EditText = findViewById(R.id.et_search)
        edtxtx_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val insertedtext = edtxtx_search.text.toString()
                val SharedPreferences = getSharedPreferences("SearchFilter", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = SharedPreferences.edit()
                editor.apply{
                    putString("SEARCH_FILTER", insertedtext)
                }.apply()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                ContactFragment().search(s)
            }
        })
    }


    private fun loadData(){
        val sharedPreferences = getSharedPreferences("SearchFilter", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("SEARCH_FILTER", null)

        val ed_text: EditText = findViewById(R.id.et_search)
        ed_text.setText(savedString)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.search_tb_menu, menu)
        return true
    }

    fun onCellClickListener(data: Contact){
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Photo Link", data.phone)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Номер скопирован", Toast.LENGTH_SHORT).show()
    }
}









